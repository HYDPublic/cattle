package io.cattle.platform.resource.pool;

import java.util.List;

public class PooledResourceOptions {

    List<String> requestedItems;
    String requestedItem;
    String qualifier = ResourcePoolManager.DEFAULT_QUALIFIER;
    int count = 1;

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public PooledResourceOptions withQualifier(String qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public List<String> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<String> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PooledResourceOptions withCount(int count) {
        this.count = count;
        return this;
    }

    public String getRequestedItem() {
        return requestedItem;
    }

    public void setRequestedItem(String requestedItem) {
        this.requestedItem = requestedItem;
    }

}
