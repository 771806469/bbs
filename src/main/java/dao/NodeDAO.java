package dao;

import entity.Node;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBHelp;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class NodeDAO {

    private static Logger logger = LoggerFactory.getLogger(NodeDAO.class);

    public List<Node> findAllNode() {
        String sql = "select id,nodename,topicnum from t_node";
        return DBHelp.query(sql,new BeanListHandler<>(Node.class));
    }

    public void update(Node node) {
        String sql = "update t_node set nodename=?,topicnum=? where id=? ";
        DBHelp.update(sql,node.getNodeName(),node.getTopicNum(),node.getId());
    }

    public Node findNodeById(Integer nodeId) {
        String sql = "select id,nodename,topicnum from t_node where id=?";
        return DBHelp.query(sql,new BeanHandler<>(Node.class),nodeId);
    }

    public void updateById(Integer nodeId) {
        Node node = findNodeById(nodeId);
        node.setTopicNum(node.getTopicNum()+1);
        logger.debug("nodeName为{}的节点帖子数量增加一个",node.getNodeName());
        update(node);
    }
}
