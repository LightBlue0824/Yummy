package edu.nju.yummy.service;

import edu.nju.yummy.dto.ModifyShopInfoDto;

import java.util.List;
import java.util.Map;

public interface AdminService {
    String login(String password);

    List<ModifyShopInfoDto> loadModifyShopInfoList();

    String approveModifyShopInfo(int modifyShopInfoId);

    String rejectModifyShopInfo(int modifyShopInfoId);

    int loadShopNum();

    int loadUserNum();

    double loadTotalIncome();

    double loadMonthIncome();

    Map<String,List> loadStatisticsChartData();
}
