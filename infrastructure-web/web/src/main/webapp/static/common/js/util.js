/**
 * 工具库
 * Created by suyl on 2016/3/22.
 */

var util = {

    /**
     * 时间转换
     * 直接传入从数据库读取的 Date类型的对象 以及时间格式（"yyyy-MM-dd hh:mm:ss"）
     * 返回格式化的字符串
     * @param date
     */
    dateFormatStr:function(date,ff) {
        Date.prototype.Format = function(fmt)
        {
            var o = {
                "M+" : this.getMonth()+1,                 //月份
                "d+" : this.getDate(),                    //日
                "h+" : this.getHours(),                   //小时
                "m+" : this.getMinutes(),                 //分
                "s+" : this.getSeconds(),                 //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S"  : this.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }

        var dt = new Date(date);
        var dateStr = dt.Format(ff);
        return dateStr;
    },

    /**
     * str转Amounts
     * 字符串 转金额
     */
    strFormatAmount:function(str,divNumber){
        str += "";
        if(str.lastIndexOf(".") == -1){
            if(divNumber == 100)
                str = util.accDiv(str,divNumber).toFixed(2);
            else if(divNumber ==1000 ){
                str = util.accDiv(str,divNumber).toFixed(3);
            }

        }
        str +="";
        var dor = str.split(".")[0];
        var show1 = dor.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('');
        return show1+"."+str.split(".")[1];
    },

    /**
     * js 除法
     * @param arg1
     * @param arg2
     * @returns {number}
     */
    accDiv:function (arg1,arg2){
        //给Number类型增加一个div方法，调用起来更加方便。
        Number.prototype.div = function (arg){
            return accDiv(this, arg);
        }
        var t1=0,t2=0,r1,r2;
        try{t1=arg1.toString().split(".")[1].length}catch(e){}
        try{t2=arg2.toString().split(".")[1].length}catch(e){}
        with(Math){
            r1=Number(arg1.toString().replace(".",""))
            r2=Number(arg2.toString().replace(".",""))
            return (r1/r2)*pow(10,t2-t1);
        }
    },
    /**
     * js 乘法
     *乘法函数，用来得到精确的乘法结果
     *说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
     *调用：accMul(arg1,arg2)
     *返回值：arg1乘以arg2的精确结果
     */
    accMul:function(arg1,arg2){
        //给Number类型增加一个mul方法，调用起来更加方便。
        Number.prototype.mul = function (arg){
            return accMul(arg, this);
        }
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    },

    /**
     * js 加法
     *加法函数，用来得到精确的加法结果
     *说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     *调用：accAdd(arg1,arg2)
     *返回值：arg1加上arg2的精确结果
     */
    accAdd:function(arg1,arg2){
        //给Number类型增加一个add方法，调用起来更加方便。
        Number.prototype.add = function (arg){
            return accAdd(arg,this);
        }
        var r1,r2,m;
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2))
        return (arg1*m+arg2*m)/m
    },

    /**
     *  js 减法
     * @param arg1
     * @param arg2
     * @returns {string}
     * @constructor
     */
    subtr:function(arg1,arg2){
        var r1,r2,m,n;
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2));
        //last modify by deeka
        //动态控制精度长度
        n=(r1>=r2)?r1:r2;
        return ((arg1*m-arg2*m)/m).toFixed(n);
    },

    /**
     * 把含有逗号和顿号的金额转换成以分为单位的金额，用于数据提交
     * @param str
     * @constructor
     */
    AmountFormatStr:function(amount){
        amount = amount.replace(/,/g,"");
        return util.accMul(amount,100);
    }



}
