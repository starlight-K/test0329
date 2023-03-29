package com.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.entity.Balance;
import com.test.mapper.BalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("money")
@Transactional
public class MoneyController {
    @Autowired
    BalanceMapper balanceMapper;

    // 查询余额
    @RequestMapping("balance/{uid}")
    public String balance(@PathVariable(required = false) Integer uid, RedirectAttributes ra) {
        List list = findBalance(uid);
        Balance balance = (Balance) list.get(list.size() - 1);
        System.out.println(balance.getBalance());

        ra.addFlashAttribute("balance", balance.getBalance());
        return "redirect:/money/tobank";
    }

    /**
     * @Documented 消费API
     * @param uid   用户id
     * @param ra：   记录在页面保存的信息
     * @return 重定向到页面
     */
    @RequestMapping("consume/{uid}")
    public String consume(Double money, @PathVariable(required = false) Integer uid, RedirectAttributes ra) {
        List list = findBalance(uid);
        System.out.println(list);
        Balance balance = (Balance) list.get(list.size() - 1);
        // 余额
        Double b = balance.getBalance();
        if (b < money) ra.addFlashAttribute("msg", "余额不足");
        else {
            // 将消费记录存入余额表中
            // 余额
            b = b - money;
            System.out.println("消费"+b);
            // 当前时间
            java.util.Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(day);
            //记录
            String detail = "消费" + money + "元";
            Balance newBalance = new Balance(null, uid, b, format, detail);
            balanceMapper.insert(newBalance);
            ra.addFlashAttribute("msg", "消费成功");
        }
        return "redirect:/money/tobank";
    }

    /**
     * @Documented  退款API
     * @param uid   用户id
     * @param ra    记录在页面保存的信息
     * @return 重定向到页面
     */
    @RequestMapping("drawback/{uid}")
    public String drawback(Double money, @PathVariable(required = false) Integer uid, RedirectAttributes ra) {
        // 获取最后一次消费记录
        List list = findBalance(uid);
        System.out.println(list);
        Balance balance = (Balance) list.get(list.size() - 1);
        String s = checkNum(balance.getDetail());
        double v = Double.parseDouble(s);
        if (money > v) {
            ra.addFlashAttribute("msg", "退款金额大于消费金额，退款失败！");
        } else {
            // 更新余额表记录
            // 余额
            Double b = balance.getBalance();
            b = b + money;
            // 当前时间
            java.util.Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(day);
            //记录
            String detail = "退款" + money + "元";
            Balance newBalance = new Balance(null, uid, b, format, detail);
            balanceMapper.insert(newBalance);
            ra.addFlashAttribute("msg", "退款成功");
        }
        return "redirect:/money/tobank";

    }

    /**
     * @Documented 查询余额变动细明API
     * @param uid         用户id
     * @param ra          记录在页面保存的信息
     * @return 重定向到页面
     */
    @RequestMapping("detail/{uid}")
    public String detail(@PathVariable(required = false) Integer uid, RedirectAttributes ra) {
        List balance = findBalance(uid);
        System.out.println(balance);
        ra.addFlashAttribute("list", balance);
        return "redirect:/money/tobank";
    }

    /**
     * @return 带参数跳转页面
     */
    @RequestMapping("tobank")
    public String tobank() {
        return "bank";
    }

    /**
     * @param uid 接受用户id
     * @return 返回根据用户id查询到的消费细明表
     */
    public List findBalance(Integer uid) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("uid", uid);
        return balanceMapper.selectList(qw);
    }

    /**
     * @Documented 含有小数或数字的字符串
     * @return 取字符串中的整数或者小数
     */
    public String checkNum(String str) {
//        String str = "abcd123和345.56jia567.23.23jian345and23or345.56";
        //先判断有没有整数，如果没有整数那就肯定就没有小数
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        String result = "";
        if (m.find()) {
            Map<Integer, String> map = new TreeMap();
            Pattern p2 = Pattern.compile("(\\d+\\.\\d+)");
            m = p2.matcher(str);
            //遍历小数部分
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                //排除小数的整数部分和另一个整数相同的情况下，寻找整数位置出现错误的可能，还有就是寻找重复的小数
                // 例子中是排除第二个345.56时第一个345.56产生干扰和寻找整数345的位置时，前面的小数345.56会干扰
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            //遍历整数
            Pattern p3 = Pattern.compile("(\\d+)");
            m = p3.matcher(str);
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                //排除jia567.23.23在第一轮过滤之后留下来的jia.23对整数23产生干扰
                if (String.valueOf(str.charAt(i - 1)).equals(".")) {
                    //将这个字符串删除
                    str = str.substring(0, i - 1) + str.substring(i + result.length());
                    continue;
                }
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            result = "";
            for (Map.Entry<Integer, String> e : map.entrySet()) {
                result += e.getValue() + ",";
            }
            result = result.substring(0, result.length() - 1);
        } else {
            result = "";
        }
        return result;
    }
}
