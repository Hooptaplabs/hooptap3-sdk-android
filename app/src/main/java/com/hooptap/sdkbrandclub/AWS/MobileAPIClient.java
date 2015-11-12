/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.hooptap.sdkbrandclub.AWS;



import com.amazonaws.mobileconnectors.apigateway.annotation.Operation;
import com.amazonaws.mobileconnectors.apigateway.annotation.Service;
import com.hooptap.a.Callback;
import com.hooptap.a.client.Response;
import com.hooptap.sdkbrandclub.AWS.model.Item;

import java.util.List;

@Service(endpoint = "https://25unt9h64h.execute-api.us-west-2.amazonaws.com/dev")
public interface MobileAPIClient {
    
    /**
     * 
     * 
     * @return Item
     */
    @Operation(path = "/items", method = "GET")
    List<Item> itemsGet();

    @Operation(path = "/items", method = "GET")
    void itemsGetCallback(Callback<Response>callback);
    
}
