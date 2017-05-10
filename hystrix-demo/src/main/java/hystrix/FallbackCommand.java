package hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class FallbackCommand extends HystrixCommand<String> {
	private static final String COMMAND_GROUP = "default";

	public FallbackCommand(){
		super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
	}

	@Override
	protected String run() throws Exception{
		throw new RuntimeException("I will always fail");
	}

	@Override
	protected String getFallback(){
		return "Powered by fallback";
	}
} 