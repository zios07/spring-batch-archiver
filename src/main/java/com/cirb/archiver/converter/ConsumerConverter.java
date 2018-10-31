/**
 * 
 */
package com.cirb.archiver.converter;

import com.cirb.archiver.domain.Consumer;
import com.cirb.archiver.vos.ConsumerVO;

/**
 * @author Zkaoukab
 *
 */
public class ConsumerConverter {
	
	public static ConsumerVO consumerToConsumerVO(Consumer consumer) {
		return new ConsumerVO(consumer.getId(), consumer.getAction(), consumer.getApplicationId(), consumer.getEndPoint(), consumer.getError(), consumer.getInternalMessageId(), consumer.getExternalMessageId(), consumer.getExternalMessageContext(), consumer.getInstitute(), consumer.getKeyType(), consumer.getRequest(), consumer.getRequestTimestamp(), consumer.getResponse(),
				consumer.getResponseTimestamp(), consumer.getTransactionId(), consumer.getExternalTimestamp());
	}
	
}
