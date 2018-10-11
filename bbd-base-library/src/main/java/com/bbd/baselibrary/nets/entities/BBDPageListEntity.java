package com.bbd.baselibrary.nets.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * created by Damon on 2017/7/4 9:19
 * <p>
 * description:
 */
public class BBDPageListEntity<T> {

    @SerializedName(value = "hasNextPage")
    private boolean hasNextPage;

    @SerializedName(value = "total", alternate = {"totalAmount"})
    private int total;

    @SerializedName(value = "data", alternate = {"body", "list", "content"})
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
