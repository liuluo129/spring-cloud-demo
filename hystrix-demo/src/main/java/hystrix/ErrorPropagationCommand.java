package hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ErrorPropagationCommand extends HystrixCommand<String> {
	private static final String COMMAND_GROUP = "default";

	protected ErrorPropagationCommand(){
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
	}

	@Override
	protected String run() throws Exception{
		throw new HystrixBadRequestException("I fail differently", new RuntimeException("I will always fail"));
	}

	@Override
	protected String getFallback(){
		return "Powered by fallback";
	}
} 