package hystrix;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import rx.Observer;

public class CommandHelloObserver implements Observer<String> {
	// Needed for communication between observer and test case
	private final AtomicReference<String> aRef;
	private final Semaphore semaphore;

	public CommandHelloObserver(AtomicReference<String> aRef, Semaphore semaphore){
		this.aRef = aRef;
		this.semaphore = semaphore;
	}

	@Override
	public void onCompleted(){ // Not needed here
	}

	@Override
	public void onError(Throwable e){ // Not needed here
	}

	@Override
	public void onNext(String s){
		aRef.set(s);
		semaphore.release();
	}
}