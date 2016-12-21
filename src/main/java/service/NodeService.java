package service;

import dao.NodeDAO;
import entity.Node;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class NodeService {

    private NodeDAO nodeDAO = new NodeDAO();

    public List<Node> findAllNode() {
       return nodeDAO.findAllNode();
    }

}
