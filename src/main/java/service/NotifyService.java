package service;

import dao.NotifyDAO;
import entity.Notify;
import exception.ServiceException;
import org.joda.time.DateTime;
import util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class NotifyService {

    NotifyDAO notifyDAO = new NotifyDAO();

    public List<Notify> findListbyUserId(Integer userId) {
        return notifyDAO.findListbyUserId(userId);
    }

    public void updateNotifyByIds(String ids) {

        String[] idArray = ids.split(",");
        List<String> idError = new ArrayList<>();
        for(int i = 0;i < idArray.length;i++) {
            if(StringUtils.isNumeric(idArray[i])) {
                Notify notify = notifyDAO.findById(Integer.valueOf(idArray[i]));
                if(notify != null) {
                    notify.setReadTime(new Timestamp(new DateTime().getMillis()));
                    notify.setState(Notify.NOTIFY_STATE_READED);
                    notifyDAO.updateNotify(notify);
                } else {
                    idError.add(idArray[i]);
                }
            } else {
                idError.add(idArray[i]);
            }
        }
        if(!idError.isEmpty()) {
            throw new ServiceException("参数错误！");
        }

    }
}
