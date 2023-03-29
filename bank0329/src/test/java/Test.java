import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
//        String str = "jidfig11.0";
//        String regEx = "[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        String result = m.replaceAll("").trim();
//        System.out.println(result);

        String s = checkNum("asdfas11.0asdf");
        System.out.println(s);
    }

    //取字符串中的数字
    public static String checkNum(String str) {
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
            result = result.substring(0, result.length()-1);
        } else {
            result = "";
        }
        return result;
    }

}
