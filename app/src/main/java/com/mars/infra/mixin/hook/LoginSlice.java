package com.mars.infra.mixin.hook;

import android.util.Log;

import com.mars.infra.mixin.annotations.Slice;
import com.mars.infra.mixin.annotations.Proxy;
import com.mars.infra.mixin.annotations.SliceProxyInsn;
import com.mars.infra.mixin.lib.Login;
import com.mars.infra.mixin.utils.LoginUtils;

/**
 * Created by Mars on 2022/3/25
 * <p>
 * 背景：Login#login登录方法中并未对用户名和密码做有效性判断
 * hook Login对象的login实例方法，需要在hook方法参数中添加this对象
 */
@Slice
class LoginSlice {


    /**
     * hook Login的init静态方法，测试MixinProxyInsn的handle方法
     */
//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "init", isStatic = true)
    public static void hookInit(String username, String password, int code) {
        System.out.println("hook Login#init success, start--------------");
        SliceProxyInsn.invoke(username, password, code);
        System.out.println("LoginMixin#hookInit, end-------------------------");
    }


    @Proxy(owner = "com/mars/infra/mixin/lib/Login", name = "login", isStatic = false)
    public static void hookLogin(Object obj, String username, String password) {
        System.out.println("start hookLogin invoke.");
        if (LoginUtils.check(username, password)) {
            SliceProxyInsn.invoke(obj, username, password);
        } else {
            Log.e("Login", "用户名和密码不正确.");
        }
    }


//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "login", isStatic = false)
    public static void hookLogin_2(Object obj, String username, String password) {
        System.out.println("hookLogin_2 invoke.");
        Login login = (Login) obj;
        if (login.check(username, password)) {
            SliceProxyInsn.invoke(obj, username, password);
        } else {
            Log.e("Login", "用户名和密码不正确.");
        }
    }

//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "logout", isStatic = true)
    public static void hookLogout(int code) {
        System.out.println("LoginMixin#hookLogout, invoke hookLogout, code = " + code);
        SliceProxyInsn.invoke(code);
        System.out.println("LoginMixin#hookLogout, logout success");
    }

//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "logout_2", isStatic = true)
    public static boolean hookLogout_2(int code) {
        System.out.println("LoginMixin#hookLogout_2, invoke hookLogout, code = " + code);
        boolean res = (boolean) SliceProxyInsn.invoke(code);
        System.out.println("LoginMixin#hookLogout_2, logout success");
        return res;
    }

//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "logout_3", isStatic = false)
    public static void hookLogout_3(Object obj, int code) {
        System.out.println("LoginMixin#hookLogout_3, invoke hookLogout_3, code = " + code);
//        Login login = (Login) obj;
        SliceProxyInsn.invoke(obj, code);
        System.out.println("LoginMixin#hookLogout_3, logout success");
    }

    /**
     *
     * @param obj
     * @param code
     * @return
     */
//    @Proxy(owner = "com.mars.infra.mixin.lib.Login", name = "logout_4", isStatic = false)
    public static boolean hookLogout_4(Object obj, int code) {
        System.out.println("LoginMixin#hookLogout_4, invoke hookLogout_4, code = " + code);
//        Login login = (Login) obj;
        boolean res = (boolean) SliceProxyInsn.invoke(obj, code);
        System.out.println("LoginMixin#hookLogout_4, logout success");
        return res;
    }
}
