package entity;


import org.joda.time.DateTime;

import java.sql.Timestamp;

public class Topic {

    private Integer id;
    private String title;
    private String content;
    private Timestamp createTime;
    private Integer clickNum;
    private Integer favNum;
    private Integer thanksNum;
    private Integer replyNum;
    private Timestamp lastReplyTime;
    private Integer userId;
    private Integer nodeId;

    private User user;
    private Node node;

    public Topic() {}

    public Topic(String title, String content, Integer userId, Integer nodeId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.nodeId = nodeId;
    }

    public boolean isEditor(){
        DateTime dateTime = new DateTime(getCreateTime());
        if(dateTime.plusMinutes(5).isAfterNow() && (getReplyNum() == 0)) {
            return true;
        }
        return false;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getFavNum() {
        return favNum;
    }

    public void setFavNum(Integer favNum) {
        this.favNum = favNum;
    }

    public Integer getThanksNum() {
        return thanksNum;
    }

    public void setThanksNum(Integer thanksNum) {
        this.thanksNum = thanksNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Timestamp getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Timestamp lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
