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


public class Item {
    private String itemID = null;
    private String title = null;
    private String imageURL = null;
    private String description = null;

    /**
     * Gets itemID
     *
     * @return itemID
     **/
    public String getItemID() {
        return itemID;
    }

    /**
     * Sets the value of itemID.
     *
     * @param itemID the new value
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * Gets title
     *
     * @return title
     **/
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of title.
     *
     * @param title the new value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets imageURL
     *
     * @return imageURL
     **/
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the value of imageURL.
     *
     * @param imageURL the new value
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Gets description
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of description.
     *
     * @param description the new value
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
