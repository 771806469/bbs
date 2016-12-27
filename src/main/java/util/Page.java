package util;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class Page<T> {

    //数据总条数
    private int count;
    //每页显示的总条数
    private int pageSize = 8;
    //当前页数据
    private List<T> pageList;
    //当前页开始行数
    private int start;
    //当前页数
    private int pageNum;
    //总页数
    private int pageCount;

    public Page(){}

    public Page(int count,int pageNum){
        this.count = count;
        this.start = (pageNum - 1) * pageSize;
        pageCount = count/pageSize;

        if(count%pageSize != 0) {
            pageCount++;
        }

        if(pageNum <= 0) {
            pageNum = 1;
        } else if(pageNum > pageCount) {
            pageNum = pageCount;
        }
        this.pageNum = pageNum;

    }

    public int getTotals() {
        return count;
    }

    public void setTotals(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
