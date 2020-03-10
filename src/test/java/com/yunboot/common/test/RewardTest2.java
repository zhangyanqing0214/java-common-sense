package com.yunboot.common.test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 固定抽奖概率抽奖
 */
public class RewardTest2
{
    public static void main(String[] args)
    {
        final Map<String, Integer> awardStockMap = initStockData();
        // 权重默认等于库存
        final Map<String, Integer> awardWeightMap = new ConcurrentHashMap<>(awardStockMap);
        final Map<String, Integer> initAwardStockMap = new ConcurrentHashMap<>(awardStockMap);

        int drawNum = 50780; // 理论可以抽完所有奖品所需抽奖次数 = 奖品数×中奖概率导数 = 7617*100/15
        final int threshold = 15; //中奖概率 15%
        Map<String, Integer> dailyWinCountMap = new ConcurrentHashMap<>(); // 每天实际中奖计数

        for (int j = 0; j < drawNum; j++)
        { // 模拟每次抽奖
            //确定是否中奖
            int randNum = new Random().nextInt(100);
            if (randNum > threshold)
            {
                dailyWinCountMap.compute("未中奖", (k, v) -> v == null ? 1 : v + 1);
                continue; //未中奖
            }
            //中奖 确定是哪个奖品
            //排除掉库存为0的奖品
            Map<String, Integer> awardWeightHaveStockMap = awardWeightMap.entrySet().stream().filter(e -> awardStockMap.get(e.getKey()) > 0).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            if (awardWeightHaveStockMap.isEmpty())
            { //奖池已为空
                System.out.printf("第%d次抽奖 奖品已被抽完%n", j);
                break;
            }
            int totalWeight = (int) awardWeightHaveStockMap.values().stream().collect(Collectors.summarizingInt(i -> i)).getSum();
            randNum = new Random().nextInt(totalWeight);
            int prev = 0;
            String chooseAward = null;
            for (Map.Entry<String, Integer> e : awardWeightHaveStockMap.entrySet())
            {
                if (randNum >= prev && randNum < prev + e.getValue())
                {
                    chooseAward = e.getKey(); //落入此区间 中奖
                    dailyWinCountMap.compute(chooseAward, (k, v) -> v == null ? 1 : v + 1);
                    break;
                }
                prev = prev + e.getValue();
            }
            //减小库存
            awardStockMap.compute(chooseAward, (k, v) -> v - 1);
        }
        System.out.println("每日各奖品中奖计数: "); // 每日各奖品中奖计数
        dailyWinCountMap.entrySet().stream().sorted((e1, e2) -> e2.getValue() - e1.getValue()).forEach(System.out::println);
        awardStockMap.forEach((k, v) ->
        {
            if (v > 0)
            {
                System.out.printf("奖品：%s, 总库存： %d, 剩余库存： %d%n", k, initAwardStockMap.get(k), v);
            }
        });
    }

    private static Map<String, Integer> initStockData()
    {
        final Map<String, Integer> awardStockMap = new ConcurrentHashMap<>();
        awardStockMap.put("1", 3000);
        awardStockMap.put("2", 2000);
        awardStockMap.put("3", 1500);
        awardStockMap.put("5", 1000);
        awardStockMap.put("10", 100);
        awardStockMap.put("20", 10);
        awardStockMap.put("50", 5);
        awardStockMap.put("100", 2);
        return awardStockMap;
    }
}