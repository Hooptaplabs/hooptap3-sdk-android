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

package com.hooptap.sdkbrandclub.AWS.model;


import java.util.*;

public class Folder extends Item{
    private String category = null;
    private List<Item> items = new ArrayList<Item>() ;

    /**
     * Gets category
     *
     * @return category
     **/
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of category.
     *
     * @param category the new value
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets items
     *
     * @return items
     **/
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the value of items.
     *
     * @param items the new value
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

}