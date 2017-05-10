package hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class RetryCommand extends HystrixCommand<String> {
	private static final String COMMAND_GROUP = "default";

	private int maxRetry = 3;

	private int retry = 0;

	public RetryCommand(int hasTries, int maxRetry){

		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
					  .andCommandPropertiesDefaults(
							  // we default to a 100ms timeout for secondary
							  HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100000)));
		this.retry = hasTries;
		this.maxRetry = maxRetry;
	}

	@Override
	protected String run() {
		retry ++;
		if (retry < maxRetry) {
			throw new RuntimeException("I fail time: " + retry);
		}
		throw new RuntimeException("has try time: " + maxRetry);
	}

	// 只能fallback 一次
	@Override
	protected String getFallback(){
		if (retry != maxRetry) {
			return new RetryCommand(retry, maxRetry).execute();
		}
		return "service down";
	}
} 