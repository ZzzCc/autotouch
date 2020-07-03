package com.hdf.autotouch.util;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import java.math.BigDecimal;

public class FormatUtils {

    public static String formatDeecimal(String decimal, int digit) {
        BigDecimal bigDecimal = new BigDecimal(decimal);
        return bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String invisibleUsername(String s) {
        if (RegexUtils.isEmail(s)) {
            return invisibleEmail(s);
        } else {
            return invisiblePhone(s);
        }
    }

    private static String invisiblePhone(String s) {
        if (!StringUtils.isEmpty(s)) {
            if (s.length() >= 11) {
                return s.substring(0, s.length() - 8) + "****" + s.substring(s.length() - 4);
            } else if (s.length() >= 6) {
                return s.substring(0, s.length() - 6) + "****" + s.substring(s.length() - 2);
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

    private static String invisibleEmail(String s) {
        if (!StringUtils.isEmpty(s)) {
            if (s.contains("@")) {
                String left = s.split("@")[0];
                String right = s.split("@")[1];
                if (left.length() > 2) {
                    left = left.substring(0, 1) + "****" + left.substring(left.length() - 1);
                }
                return left + "@" + right;
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

    public static String invisibleString(String s) {
        if (!StringUtils.isEmpty(s)) {
            if (s.length() > 16) {
                String left = s.substring(0, 8);
                String right = s.substring(s.length() - 8);
                return left + "****" + right;
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

    //用户的算力等级(0：暂无身份 1:服务器 2: 矿池 3:矿场 4:社区节点 5:城市节点 6:超级节点:7：全球节点)
    public static String getLevelType(String s) {
        switch (s) {
            case "0":
                return "暂无身份";
            case "1":
                return "服务器";
            case "2":
                return "矿池";
            case "3":
                return "社区节点";
            case "4":
                return "城市节点";
            case "5":
                return "超级节点";
            case "6":
                return "全球节点";
            case "7":
                return "t l a";
            default:
                break;
        }
        return "";
    }

    //     4： //内部转账 5：//提现      6：//充币     7：//购买存力     8：//存力释放 9：//级差收益
    //    10：//业绩奖   11：//后台充值 12： //托管费 13： //购买社区节点 14： //手续费 15：// 撤销提币
    public static String getBillType(int s) {
        switch (s) {
            case 4:
                return "内部转账";
            case 5:
                return "提现";
            case 6:
                return "充币";
            case 7:
                return "购买存力";
            case 8:
                return "存力释放";
            case 9:
                return "级差收益";
            case 10:
                return "业绩奖";
            case 11:
                return "后台充值";
            case 12:
                return "托管费";
            case 13:
                return "购买社区节点";
            case 14:
                return "手续费";
            case 15:
                return "撤销提币";
            case 16:
                return "兑换扣除";
            case 17:
                return "兑换增加";
            case 18:
                return "提现驳回";
            default:
                break;
        }
        return "";
    }

    public static String formatEndZero(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");
            str = str.replaceAll("[.]$", "");
        }
        return str;
    }
}
