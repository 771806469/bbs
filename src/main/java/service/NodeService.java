package service;

import dao.NodeDAO;
import entity.Node;
import exception.ServiceException;

import java.util.List;

public class NodeService {

    private NodeDAO nodeDAO = new NodeDAO();

    public List<Node> findAllNode() {
        return nodeDAO.findAllNode();
    }

    public Node findById(Integer nodeId) {

        Node node = nodeDAO.findNodeById(nodeId);
        if (node == null) {
            throw new ServiceException("该节点不存在或已被删除！");
        } else {
            return node;
        }

    }

    public String validateNodeName(Integer nodeId, String nodeName) {

        Node node = nodeDAO.findNodeByNodeName(nodeName);
        if (node == null) {
            return "true";
        } else {
            if(!"0".equals(nodeId)) {
                if (node.getId().equals(nodeId)) {
                    return "true";
                } else {
                    return "false";
                }
            } else {
                return "false";
            }
        }

    }

    public void updateNode(Integer nodeId, String nodeName) {

        Node node = nodeDAO.findNodeById(nodeId);
        if (node != null) {
            node.setNodeName(nodeName);
            nodeDAO.update(node);
        } else {
            throw new ServiceException("该节点不存在或已被删除！");
        }

    }

    public void delById(Integer nodeId) {

        Node node = nodeDAO.findNodeById(nodeId);
        if (node == null) {
            throw new ServiceException("该节点不存在或已被删除！");
        } else {
            if (node.getTopicNum() > 0) {
                throw new ServiceException("该节点下已有帖子不允许删除！");
            } else {
                nodeDAO.delById(nodeId);
            }
        }

    }

    public void save(String nodeName) {
        nodeDAO.save(nodeName);

    }
}
