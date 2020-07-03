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
import com.hdf.autotouch.util.SPManager;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : HttpManager
 * </pre>
 */
public class HttpManager {

    private ApiService mApiService;

    public HttpManager() {
        this.mApiService = HttpHelper.getInstance().getServer();
    }

    public Observable<Response<HttpResult<Object>>> getCode(String prefix, String account, String ticket, String randomStr) {
        return mApiService.getCode(prefix, account, ticket, randomStr);
    }

    public Observable<Response<HttpResult<Object>>> getCheckVerifyCode(String account, String prefix, String verifyCode) {
        return mApiService.getCheckVerifyCode(account, prefix, verifyCode);
    }

    public Observable<Response<HttpResult<User>>> login(String prefix, String account, String code, String password) {
        return mApiService.login(prefix, account, code, password);
    }

    public Observable<Response<HttpResult<User>>> register(String prefix, String account, String verifyCode, String password, String inviteYards) {
        return mApiService.register(prefix, account, verifyCode, password, inviteYards);
    }

    public Observable<Response<HttpResult<Object>>> forgetPassword(String prefix, String account, String code, String password) {
        return mApiService.forgetPassword(prefix, account, code, password);
    }

    public Observable<Response<HttpResult<User>>> getUser() {
        return mApiService.getUser(SPManager.getId());
    }

    public Observable<Response<HttpResult<HomeData>>> getHomeData() {
        return mApiService.getHomeData();
    }

    public Observable<Response<HttpResult<List<Market>>>> getMarket(int page) {
        return mApiService.getMarket(String.valueOf(page));
    }

    public Observable<Response<HttpResult<String>>> getChargeAddress() {
        return mApiService.getChargeAddress(SPManager.getId());
    }

    public Observable<Response<HttpResult<WithdrawAddress>>> getWithdrawAddress() {
        return mApiService.getWithdrawAddress(SPManager.getId());
    }

    public Observable<Response<HttpResult<Object>>> binding(String address) {
        return mApiService.binding(SPManager.getId(), address);
    }

    public Observable<Response<HttpResult<Object>>> removeBinding() {
        return mApiService.removeBinding(SPManager.getId());
    }

    public Observable<Response<HttpResult<Object>>> withdraw(String withdrawAddress, String amount, String node, String payPassword) {
        return mApiService.withdraw(SPManager.getId(), withdrawAddress, amount, node, payPassword);
    }

    public Observable<Response<HttpResult<List<Notice>>>> getNotice(int pageNum) {
        return mApiService.getNotice(String.valueOf(pageNum));
    }

    public Observable<Response<HttpResult<MineField>>> getMineField() {
        return mApiService.getMineField();
    }

    public Observable<Response<HttpResult<Object>>> purchaseMineField(String payPassword) {
        return mApiService.purchaseMineField(SPManager.getId(), payPassword);
    }

    public Observable<Response<HttpResult<List<CollectTransferDetail>>>> getCollectTransferDetail(int pageNum) {
        return mApiService.getCollectTransferDetail(SPManager.getId(), String.valueOf(pageNum));
    }

    public Observable<Response<HttpResult<Object>>> transfer(String prefix, String phone, String coin, String amount, String payPassword) {
        return mApiService.transfer(SPManager.getId(), prefix, phone, coin, amount, payPassword);
    }

    public Observable<Response<HttpResult<MyAssets>>> getTransaction(int pageNum) {
        return mApiService.getTransaction(SPManager.getId(), String.valueOf(pageNum));
    }

    public Observable<Response<HttpResult<Object>>> revocation(String txId) {
        return mApiService.revocation(SPManager.getId(), txId);
    }

    public Observable<Response<HttpResult<List<String>>>> getMallPic() {
        return mApiService.getMallPic();
    }

    public Observable<Response<HttpResult<List<Goods>>>> getGoods() {
        return mApiService.getGoods();
    }

    public Observable<Response<HttpResult<List<Address>>>> getAddress(int page) {
        return mApiService.getAddress(SPManager.getId(), String.valueOf(page));
    }

    public Observable<Response<HttpResult<Object>>> addAddress(String name, String prefix, String phone) {
        return mApiService.addAddress(SPManager.getId(), name, prefix, phone);
    }

    public Observable<Response<HttpResult<Object>>> editAddress(String aId, String name, String prefix, String phone) {
        return mApiService.editAddress(SPManager.getId(), aId, name, prefix, phone);
    }

    public Observable<Response<HttpResult<Object>>> deleteAddress(String aId) {
        return mApiService.deleteAddress(SPManager.getId(), aId);
    }

    public Observable<Response<HttpResult<Order>>> submitOrder(String pid, String addressId, String num, String amount, String message) {
        return mApiService.submitOrder(SPManager.getId(), pid, addressId, num, amount, message);
    }

    public Observable<Response<HttpResult<List<Order>>>> getOrder(int status, int page) {
        return mApiService.getOrder(SPManager.getId(), String.valueOf(status), String.valueOf(page));
    }

    public Observable<Response<HttpResult<Order>>> getOrderDetail(String orderId) {
        return mApiService.getOrderDetail(orderId);
    }

    public Observable<Response<HttpResult<Order>>> payOrder(String orderId, String payPassword) {
        return mApiService.payOrder(SPManager.getId(), orderId, payPassword);
    }

    public Observable<Response<HttpResult<Object>>> cancelOrder(String orderId) {
        return mApiService.cancelOrder(SPManager.getId(), orderId);
    }

    public Observable<Response<HttpResult<MyTeam>>> myTeam() {
        return mApiService.myTeam(SPManager.getId());
    }

    public Observable<Response<HttpResult<MinePool>>> getMinePoolData(String pageNum) {
        return mApiService.getMinePoolData(SPManager.getId(), pageNum);
    }

    public Observable<Response<HttpResult<String>>> getWechat() {
        return mApiService.getWechat();
    }

    public Observable<Response<HttpResult<Object>>> getBinding(String mac) {
        return mApiService.getBinding(SPManager.getId(), mac);
    }

    public Observable<Response<HttpResult<Object>>> getChangePassword(String youngPassword) {
        return mApiService.getChangePassword(SPManager.getId(), youngPassword);
    }

    public Observable<Response<HttpResult<List<MyMill>>>> getMill(String pageNum,int status) {
        return mApiService.getMill(SPManager.getId(), pageNum,status);
    }

    public Observable<Response<HttpResult<Integer>>> getAllBinding(String orderId) {
        return mApiService.getAllBinding(orderId);
    }

    public Observable<Response<HttpResult<List<MyBill>>>> getBill(String pageNum) {
        return mApiService.getBill(SPManager.getId(), pageNum);
    }

    public Observable<Response<HttpResult<Object>>> setPayPassword(String payPassword) {
        return mApiService.setPayPassword(SPManager.getId(), payPassword);
    }

    public Observable<Response<HttpResult<VersionCode>>> getVersionCode() {
        return mApiService.getVersionCode();
    }

    public Observable<Response<HttpResult<MyMac>>> getmyMac(String pageNum) {
        return mApiService.getmyMac(SPManager.getId(), pageNum);
    }

    public Observable<Response<HttpResult<Object>>> getDeductMac(String mac, String payPassword) {
        return mApiService.getDeductMac(SPManager.getId(), mac, payPassword);
    }

    public Observable<Response<HttpResult<ExchangeRate>>> getExchangeRate(String inCoin, String outCoin) {
        return mApiService.getExchangeRate(inCoin, outCoin);
    }

    public Observable<Response<HttpResult<Exchange>>> exchange(String inCoin, String outCoin, String amount, String payPassword) {
        return mApiService.exchange(SPManager.getId(), inCoin, outCoin, amount, payPassword);
    }

    public Observable<Response<HttpResult<List<Exchange>>>> getExchangeRecord(int pageNum) {
        return mApiService.getExchangeRecord(SPManager.getId(), String.valueOf(pageNum));
    }
}
