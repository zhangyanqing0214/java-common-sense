package com.yunboot.common.test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RewardTest
{
    public static void main(String[] args)
    {   //初始化库存
        final Map<String, Integer> awardStockMap = initStock();
        //初始化权重，默认等于库存
        final Map<String, Integer> awardWeightMap = initWeight(awardStockMap);
        int userNum = 30000; // 日活用户数
        int drawNum = userNum * 3; // 每天抽奖次数 = 日活数*抽奖次数
        Map<String, Integer> dailyWinCountMap = new ConcurrentHashMap<>(); // 每天实际中奖计数
        for (int j = 0; j < drawNum; j++)
        {   // 模拟每次抽奖
            //排除掉库存为0的奖品
            Map<String, Integer> awardWeightHaveStockMap = awardWeightMap.entrySet().stream().filter(e -> awardStockMap.get(e.getKey()) > 0).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            int totalWeight = (int) awardWeightHaveStockMap.values().stream().collect(Collectors.summarizingInt(i -> i)).getSum();
            int randNum = new Random().nextInt(totalWeight); //生成一个随机数
            // 按照权重计算中奖区间
            String chooseAward = reward(awardWeightHaveStockMap, randNum);
            if(chooseAward.equals("iphone")){
                System.out.println("中奖人："+randNum);
            }
            dailyWinCountMap.compute(chooseAward, (k, v) -> v == null ? 1 : v + 1); //中奖计数
            if (!"未中奖".equals(chooseAward))
            {   //未中奖不用减库存
                awardStockMap.compute(chooseAward, (k, v) -> v - 1); //奖品库存一
                if (awardStockMap.get(chooseAward) == 0)
                {
                    System.out.printf("奖品：%s 库存为空%n", chooseAward); //记录库存为空的顺序
                }
            }
        }
        System.out.println("各奖品中奖计数: " + dailyWinCountMap); //每日各奖品中奖计数
    }

    private static String reward(Map<String, Integer> awardWeightHaveStockMap, int randNum)
    {
        int prev = 0;
        String chooseAward = null;
        for (Map.Entry<String, Integer> e : awardWeightHaveStockMap.entrySet())
        {
            if (randNum >= prev && randNum < prev + e.getValue())
            {
                chooseAward = e.getKey(); //落入该奖品区间
                break;
            }
            prev = prev + e.getValue();
        }
        return chooseAward;
    }

    private static Map<String, Integer> initWeight(Map<String, Integer> awardStockMap)
    {
        return new ConcurrentHashMap<>(awardStockMap);
    }

    private static Map<String, Integer> initStock()
    {
        final Map<String, Integer> awardStockMap = new ConcurrentHashMap<>(); // 奖品 <--> 奖品库存
        awardStockMap.put("1", 5000);
        awardStockMap.put("2", 1000);
        awardStockMap.put("3", 500);
        awardStockMap.put("5", 100);
        awardStockMap.put("iphone", 1);
        awardStockMap.put("未中奖", 59409); //6601*10 -6601
        return awardStockMap;
    }
}
