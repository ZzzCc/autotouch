package com.hdf.autotouch.http;

import com.hdf.autotouch.entity.Address;
import com.hdf.autotouch.entity.CollectTransferDetail;
import com.hdf.autotouch.entity.Exchange;
import com.hdf.autotouch.entity.ExchangeRate;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.entity.HomeData;
import com.hdf.autotouch.entity.HttpResult;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.entity.MineField;
import com.hdf.autotouch.entity.MinePool;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.MyBill;
import com.hdf.autotouch.entity.MyMac;
import com.hdf.autotouch.entity.MyMill;
import com.hdf.autotouch.entity.MyTeam;
import com.hdf.autotouch.entity.Notice;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.entity.VersionCode;
import com.hdf.autotouch.entity.WithdrawAddress;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ApiService
 * </pre>
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("user/sendCode")
    Observable<Response<HttpResult<Object>>> getCode(
            @Field("prefix") String prefix,
            @Field("account") String account,
            @Field("ticket") String ticket,
            @Field("randomStr") String randomStr);

    @FormUrlEncoded
    @POST("user/checkVerifyCode")
    Observable<Response<HttpResult<Object>>> getCheckVerifyCode(
            @Field("account") String account,
            @Field("prefix") String prefix,
            @Field("verifyCode") String verifyCode);

    @FormUrlEncoded
    @POST("user/login")
    Observable<Response<HttpResult<User>>> login(
            @Field("prefix") String prefix,
            @Field("account") String account,
            @Field("code") String code,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<Response<HttpResult<User>>> register(
            @Field("prefix") String prefix,
            @Field("account") String account,
            @Field("verifyCode") String verifyCode,
            @Field("password") String password,
            @Field("inviteYards") String inviteYards);

    @FormUrlEncoded
    @POST("user/forgetPassword")
    Observable<Response<HttpResult<Object>>> forgetPassword(
            @Field("prefix") String prefix,
            @Field("account") String account,
            @Field("code") String code,
            @Field("forgetPassword") String forgetPassword);

    @FormUrlEncoded
    @POST("user/object")
    Observable<Response<HttpResult<User>>> getUser(
            @Field("uid") String uid);

    /**
     * 首页展示
     */
    @POST("config/getHomepage")
    Observable<Response<HttpResult<HomeData>>> getHomeData();

    /**
     * 行情
     */
    @FormUrlEncoded
    @POST("market/info")
    Observable<Response<HttpResult<List<Market>>>> getMarket(
            @Field("pageNum") String pageNum);

    /**
     * 充币返回地址
     */
    @FormUrlEncoded
    @POST("tx/address")
    Observable<Response<HttpResult<String>>> getChargeAddress(
            @Field("uid") String uid);

    /**
     * 提币首页
     */
    @FormUrlEncoded
    @POST("tx/withdrawShow")
    Observable<Response<HttpResult<WithdrawAddress>>> getWithdrawAddress(
            @Field("uid") String uid);

    /**
     * 绑定地址
     */
    @FormUrlEncoded
    @POST("user/withdrawAddress")
    Observable<Response<HttpResult<Object>>> binding(
            @Field("uid") String uid,
            @Field("address") String address);

    /**
     * 解绑地址
     */
    @FormUrlEncoded
    @POST("user/relieveAddress")
    Observable<Response<HttpResult<Object>>> removeBinding(
            @Field("uid") String uid);

    /**
     * 提币
     */
    @FormUrlEncoded
    @POST("tx/withdraw")
    Observable<Response<HttpResult<Object>>> withdraw(
            @Field("uid") String uid,
            @Field("withdrawAddress") String withdrawAddress,
            @Field("amount") String amount,
            @Field("node") String node,
            @Field("payPassword") String payPassword);

    /**
     * 分页获取公告
     */
    @FormUrlEncoded
    @POST("about/getNotice")
    Observable<Response<HttpResult<List<Notice>>>> getNotice(
            @Field("pageNum") String pageNum);

    /**
     * 返回升级矿场带来的收益
     */
    @POST("mineral/identity")
    Observable<Response<HttpResult<MineField>>> getMineField();

    /**
     * 购买矿场身份
     */
    @FormUrlEncoded
    @POST("mineral/field")
    Observable<Response<HttpResult<Object>>> purchaseMineField(
            @Field("uid") String uid,
            @Field("payPassword") String payPassword);

    /**
     * 查询用户云端收币转币记录
     */
    @FormUrlEncoded
    @POST("tx/getUserTx")
    Observable<Response<HttpResult<List<CollectTransferDetail>>>> getCollectTransferDetail(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 链下转账
     */
    @FormUrlEncoded
    @POST("tx/sendTx")
    Observable<Response<HttpResult<Object>>> transfer(
            @Field("fromUid") String fromUid,
            @Field("prefix") String prefix,
            @Field("account") String phone,
            @Field("coin") String coin,
            @Field("amount") String amount,
            @Field("payPassword") String payPassword);

    /**
     * 我的资产
     */
    @FormUrlEncoded
    @POST("user/property")
    Observable<Response<HttpResult<MyAssets>>> getTransaction(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 撤销提币
     */
    @FormUrlEncoded
    @POST("tx/repealWithdraw")
    Observable<Response<HttpResult<Object>>> revocation(
            @Field("uid") String uid,
            @Field("txId") String txId);

    /**
     * 查看商城轮播图
     */
    @POST("config/get/slideshow")
    Observable<Response<HttpResult<List<String>>>> getMallPic();

    /**
     * 展示商品列表
     */
    @POST("mall/product")
    Observable<Response<HttpResult<List<Goods>>>> getGoods();

    /**
     * 查询自己的收货地址
     */
    @FormUrlEncoded
    @POST("receive/findAddress")
    Observable<Response<HttpResult<List<Address>>>> getAddress(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 新增收货地址
     */
    @FormUrlEncoded
    @POST("receive/insertAddress")
    Observable<Response<HttpResult<Object>>> addAddress(
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("prefix") String prefix,
            @Field("phone") String phone);

    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST("receive/updateAddress")
    Observable<Response<HttpResult<Object>>> editAddress(
            @Field("uid") String uid,
            @Field("aId") String aId,
            @Field("name") String name,
            @Field("prefix") String prefix,
            @Field("phone") String phone);

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST("receive/removeAddress")
    Observable<Response<HttpResult<Object>>> deleteAddress(
            @Field("uid") String uid,
            @Field("aId") String aId);

    /**
     * 购买服务器，生成订单
     */
    @FormUrlEncoded
    @POST("mall/add/order")
    Observable<Response<HttpResult<Order>>> submitOrder(
            @Field("uid") String uid,
            @Field("pid") String pid,
            @Field("addressId") String addressId,
            @Field("num") String num,
            @Field("amount") String amount,
            @Field("message") String message);

    /**
     * 订单列表，按状态分
     */
    @FormUrlEncoded
    @POST("mall/order/list")
    Observable<Response<HttpResult<List<Order>>>> getOrder(
            @Field("uid") String uid,
            @Field("status") String status,
            @Field("pageNum") String pageNum);

    /**
     * 查看单个订单详情
     */
    @FormUrlEncoded
    @POST("mall/select/one")
    Observable<Response<HttpResult<Order>>> getOrderDetail(
            @Field("orderId") String orderId);

    /**
     * 支付订单
     */
    @FormUrlEncoded
    @POST("mall/pay/order")
    Observable<Response<HttpResult<Order>>> payOrder(
            @Field("uid") String uid,
            @Field("orderId") String orderId,
            @Field("payPassword") String payPassword);

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("mall/cancel/order")
    Observable<Response<HttpResult<Object>>> cancelOrder(
            @Field("uid") String uid,
            @Field("orderId") String orderId);

    /**
     * 展示我的团队
     */
    @FormUrlEncoded
    @POST("about/myTeam")
    Observable<Response<HttpResult<MyTeam>>> myTeam(
            @Field("uid") String uid);

    /**
     * 矿池列表
     */
    @FormUrlEncoded
    @POST("mineral/pool/list")
    Observable<Response<HttpResult<MinePool>>> getMinePoolData(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 获取客服微信号
     */
    @POST("about/getWechat")
    Observable<Response<HttpResult<String>>> getWechat();

    /**
     * 绑定矿机
     */
    @FormUrlEncoded
    @POST("mall/binding")
    Observable<Response<HttpResult<Object>>> getBinding(
            @Field("uid") String uid,
            @Field("mac") String mac);

    /**
     * 修改登录密码
     */
    @FormUrlEncoded
    @POST("user/changePassword")
    Observable<Response<HttpResult<Object>>> getChangePassword(
            @Field("uid") String uid,
            @Field("youngPassword") String youngPassword);


    /**
     * 分页查询自己的矿机
     */
    @FormUrlEncoded
    @POST("user/getMill")
    Observable<Response<HttpResult<List<MyMill>>>> getMill(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum,
            @Field("status") int status);

    /**
     * 一键绑定
     */
    @FormUrlEncoded
    @POST("mall/allBinding")
    Observable<Response<HttpResult<Integer>>> getAllBinding(
            @Field("orderId") String orderId);

    /**
     * 分页展示自己的账单
     */
    @FormUrlEncoded
    @POST("user/getBill")
    Observable<Response<HttpResult<List<MyBill>>>> getBill(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 设置支付密码
     */
    @FormUrlEncoded
    @POST("user/setPayPassword")
    Observable<Response<HttpResult<Object>>> setPayPassword(
            @Field("uid") String uid,
            @Field("payPassword") String payPassword);

    /**
     * 安卓返回最新版本
     */
    @POST("about/isAndroidMaxVersion")
    Observable<Response<HttpResult<VersionCode>>> getVersionCode();

    /**
     * 我的服务器
     */
    @FormUrlEncoded
    @POST("user/myMac")
    Observable<Response<HttpResult<MyMac>>> getmyMac(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    /**
     * 补缴上月托管费
     */
    @FormUrlEncoded
    @POST("mineral/deduct")
    Observable<Response<HttpResult<Object>>> getDeductMac(
            @Field("uid") String uid,
            @Field("mac") String mac,
            @Field("payPassword") String payPassword);

    @FormUrlEncoded
    @POST("tx/exchange/rate")
    Observable<Response<HttpResult<ExchangeRate>>> getExchangeRate(
            @Field("inCoin") String inCoin,
            @Field("outCoin") String outCoin);

    @FormUrlEncoded
    @POST("tx/exchange")
    Observable<Response<HttpResult<Exchange>>> exchange(
            @Field("uid") String uid,
            @Field("inCoin") String inCoin,
            @Field("outCoin") String outCoin,
            @Field("amount") String amount,
            @Field("payPassword") String payPassword);

    @FormUrlEncoded
    @POST("/tx/exchange/history")
    Observable<Response<HttpResult<List<Exchange>>>> getExchangeRecord(
            @Field("uid") String uid,
            @Field("pageNum") String pageNum);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
