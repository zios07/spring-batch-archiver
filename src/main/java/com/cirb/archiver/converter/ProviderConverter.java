/**
 * 
 */
package com.cirb.archiver.converter;

import com.cirb.archiver.domain.Provider;
import com.cirb.archiver.vos.ProviderVO;

/**
 * @author Zkaoukab
 *
 */
public class ProviderConverter {

	public static ProviderVO providerToProviderVO(Provider provider) {
		return new ProviderVO(provider.getId(), provider.getAction(), provider.getApplicationId(), provider.getEndPoint(), provider.getError(), provider.getInternalMessageId(), provider.getExternalMessageId(), provider.getExternalMessageContext(), provider.getInstitute(), provider.getKeyType(), provider.getRequest(), provider.getRequestTimestamp(), provider.getResponse(),
				provider.getResponseTimestamp(), provider.getTransactionId(), provider.getExternalTimestamp());
	}
	
}
