package service;

import dao.NodeDAO;
import entity.Node;

import java.util.List;

public class NodeService {

    private NodeDAO nodeDAO = new NodeDAO();

    public List<Node> findAllNode() {
       return nodeDAO.findAllNode();
    }

}
