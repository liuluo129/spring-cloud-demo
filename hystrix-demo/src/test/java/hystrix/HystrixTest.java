package hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author mushui
 * @created 2017-05-08 下午7:03
 */
public class HystrixTest {

	@Test
	public void shouldGreetWorld() {
		String result = new HelloCommand("World").execute();
		assertEquals("Hello, World", result);
	}

	@Test
	public void shouldExecuteSynchronously() {
		String s = new HelloCommand("Bob").execute();
		assertEquals("Hello, Bob", s);
	}

	@Test
	public void shouldExecuteAsynchronously() {
		Future<String> f = new HelloCommand("Alice").queue();
		String s = null;
		try {
			s = f.get();
		} catch (InterruptedException | ExecutionException e) {
			// Handle Future.get() exceptions here
		}
		assertEquals("Hello, Alice", s);
	}

	@Test
	public void shouldExecuteReactiveSimple() {
		Observable<String> observable = new HelloCommand("Alice").observe();
		String s = observable.toBlocking().single();
		assertEquals("Hello, Alice", s);
	}

	@Test
	public void shouldExecuteReactiveFull() {
		// Needed for communication between observer and test case
		AtomicReference<String> aRef = new AtomicReference<>();
		Semaphore semaphore = new Semaphore(0);
		Observable<String> observable = new HelloCommand("Bob").observe();
		Observer<String> observer = new CommandHelloObserver(aRef, semaphore);
		observable.subscribe(observer);
		// Wait until observer received a result
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// Handle exceptions here
		}
		String s = aRef.get();
		assertEquals("Hello, Bob", s);
	}

	@Test
	public void shouldGetFallbackResponse() {
		String s = new FallbackCommand().execute();
		assertEquals("Powered by fallback", s);
	}

	@Test
	public void shouldGetFallbackResponse2() {
		String s = null;
		try {
			s = new ErrorPropagationCommand().execute();
			fail(); // Just to make sure
		} catch (HystrixBadRequestException e) {
			assertEquals("I fail differently", e.getMessage());
			assertEquals("I will always fail", e.getCause().getMessage());
		}
		assertNull(s); // Fallback is not triggered
	}

	@Test
	public void testRetry() {
		String s = new RetryCommand(0, 3).execute();
		assertEquals("service down", s);
	}

	@Test
	public void testSuccess() {
		assertEquals("success", new CommandThatFailsFast(false).execute());
	}

	@Test
	public void testFailure() {
		try {
			new CommandThatFailsFast(true).execute();
			fail("we should have thrown an exception");
		} catch (HystrixRuntimeException e) {
			assertEquals("failure from CommandThatFailsFast", e.getCause().getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void getGetSetGet() {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			assertEquals("ValueBeforeSet_1", new CommandUsingRequestCacheInvalidation.GetterCommand(1).execute());
			CommandUsingRequestCacheInvalidation.GetterCommand commandAgainstCache = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
			assertEquals("ValueBeforeSet_1", commandAgainstCache.execute());
			// confirm it executed against cache the second time
			assertTrue(commandAgainstCache.isResponseFromCache());
			// set the new value
			new CommandUsingRequestCacheInvalidation.SetterCommand(1, "ValueAfterSet_").execute();
			// fetch it again
			CommandUsingRequestCacheInvalidation.GetterCommand commandAfterSet = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
			// the getter should return with the new prefix, not the value from cache
			assertFalse(commandAfterSet.isResponseFromCache());
			assertEquals("ValueAfterSet_1", commandAfterSet.execute());
		} finally {
			context.shutdown();
		}
	}
}
