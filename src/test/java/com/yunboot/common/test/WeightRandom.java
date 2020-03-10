package com.yunboot.common.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightRandom
{
    static List<WeightCategory> categories = new ArrayList<>();
    private static Random random = new Random();

    public static void initData()
    {
        WeightCategory wc1 = new WeightCategory("A", 60);
        WeightCategory wc2 = new WeightCategory("B", 20);
        WeightCategory wc3 = new WeightCategory("C", 20);
        categories.add(wc1);
        categories.add(wc2);
        categories.add(wc3);
    }

    public static void main(String[] args)
    {
        initData();
        Integer weightSum = 0;
        for (WeightCategory wc : categories)
        {
            weightSum += wc.getWeight();
        }

        if (weightSum <= 0)
        {
            System.err.println("Error: weightSum=" + weightSum.toString());
            return;
        }
        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;
        for (WeightCategory wc : categories)
        {
            if (m <= n && n < m + wc.getWeight())
            {
                //随机数在分段区间内，抽中，返回结果，退出！
                System.out.println("This Random Category is " + wc.getCategory());
                break;
            }
            m += wc.getWeight();
        }

    }

}

class WeightCategory
{
    private String category;
    private Integer weight;

    public WeightCategory()
    {
        super();
    }

    public WeightCategory(String category, Integer weight)
    {
        super();
        this.setCategory(category);
        this.setWeight(weight);
    }

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
