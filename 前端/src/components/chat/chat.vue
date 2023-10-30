<template>
    <div>
        <div class="wrap">
            <!-- 头部 -->
            <div class="titleBox">
                <img src="https://img-qn-2.51miz.com/preview/element/00/01/27/97/E-1279706-3813EBF9.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/320"
                    alt="" class="head_portrait" style="margin-left: 20px; margin-right: 20px" />
                <span style="color: #fff">官方客服</span>
                <div class="stateBox2" @click="uploadState(1)">
                    <span class="state1"></span>
                    <span class="stateText">在线</span>
                </div>
            </div>
            <!-- 右边输入框和信息展示 -->
            <div class="infoList">
                <!-- 信息 -->
                <div class="infoTop" ref="scrollBox" id="box">
                    <div :class="
                        item.position == 'left' ? 'chatInfoLeft' : 'chatInfoRight'
                    " v-for="(item, index) in userInfoList" :key="index">
                        <img :src="item.url" alt="头像" class="head_portrait2" />
                        <div :class="item.position == 'left' ? 'chatLeft' : 'chatRight'">
                            <div class="text" v-html="item.info"></div>
                        </div>
                    </div>
                </div>
                <!-- 输入框 -->
                <div class="infoBottom">
                    <textarea type="textarea" class="infoInput" v-model="textarea"
                        @keydown.enter.exact="handlePushKeyword($event)" @keyup.ctrl.enter="lineFeed" />
                    <div class="fasong" @click="setUp">发送</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 在线状态
            state: 1,
            //历史信息
            userInfoList: [],
            //输入框
            textarea: "",
            //滚动条距离顶部距离
            scrollTop: 0,
        };
    },
    methods: {
        //切换客服状态
        uploadState(state) {
            if (state !== 4) {
                this.state = state;
            } else {
                this.$confirm("是否退出登录?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        this.$message({
                            type: "success",
                            message: "退出成功!",
                        });
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消退出",
                        });
                    });
            }
        },
        //发送
        setUp() {
            this.userInfoList.push({
                url: "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
                username: "test",
                info: this.textarea,
                timer: "2023/6/27",
                position: "right",
            });
            this.textarea = "";
            // 页面滚动到底部
            this.$nextTick(() => { // 一定要用nextTick
                this.setPageScrollTo();
                //页面滚动条距离顶部高度等于这个盒子的高度
                this.$refs.scrollBox.scrollTop = this.$refs.scrollBox.scrollHeight;
            })
        },
        // 监听键盘回车阻止换行并发送
        handlePushKeyword(event) {
            console.log(event);
            if (event.keyCode === 13) {
                event.preventDefault(); // 阻止浏览器默认换行操作
                this.setUp(); //发送文本
                return false;
            }
        },
        // 监听按的是ctrl + 回车，就换行
        lineFeed() {
            console.log("换行");
            this.textarea = this.textarea + "\n";
        },
        //滚动条默认滚动到最底部
        setPageScrollTo(s, c) {
            //获取中间内容盒子的可见区域高度
            this.scrollTop = document.querySelector("#box").offsetHeight;
            setTimeout((res) => {
                //加个定时器，防止上面高度没获取到，再获取一遍。
                if (this.scrollTop != this.$refs.scrollBox.offsetHeight) {
                    this.scrollTop = document.querySelector("#box").offsetHeight;
                }
            }, 100);
            //scrollTop：滚动条距离顶部的距离。
            //把上面获取到的高度座位距离，把滚动条顶到最底部
            this.$refs.scrollBox.scrollTop = this.scrollTop;
            //判断是否有滚动条,有滚动条就创建一个监听滚动事件，滚动到顶部触发srTop方法
            if (this.$refs.scrollBox.scrollTop > 0) {
                this.$refs.scrollBox.addEventListener("scroll", this.srTop);
            }
        },
        //滚动条到达顶部
        srTop() {
            //判断：当滚动条距离顶部为0时代表滚动到顶部了
            if (this.$refs.scrollBox.scrollTop == 0) {
              

            }
        },
    },
};
</script>

<style scoped>
.wrap {
    height: 80%;
    width: 55%;
    background-color: #f2f2f2;
    margin: auto;
    transform: translateY(10%);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    border-radius: 10px;
}

.titleBox {
    height: 10%;
    width: 100%;
    background-image: linear-gradient(to right, #1e76bc, #69a3d5);
    display: flex;
    align-items: center;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
}

.infoBottom {
    height: 30%;
    display: flex;
    flex-direction: column;
}

/* 输入框 */
.infoInput {
    height: 45%;
    width: 100%;
    border: none;
    resize: none;
    padding: 10px;
    box-sizing: border-box;
    background-color: #f2f2f2;
    color: #434343;
}

.fasong {
    height: 30px;
    width: 80px;
    background-color: #e8e8e8;
    text-align: center;
    line-height: 30px;
    border-radius: 4px;
    color: #58df4d;
    margin-top: 1%;
    align-self: flex-end;
    margin-right: 20px;
    cursor: pointer;
}

.infoIcon {
    height: 40px;
    width: 100%;
    display: flex;
    align-items: center;
}

.infoIcon i {
    font-size: 24px;
    color: #676767;
    margin-left: 15px;
    cursor: pointer;
}

/* 头像 */
.head_portrait {
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.head_portrait2 {
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.stateBox {
    margin-left: 20px;
    padding: 1px 8px;
    background-color: #fff;
    border-radius: 10px;
    text-align: center;
    cursor: pointer;
}

.stateBox2 {
    margin-left: 20px;
    padding: 1px 8px;
    background-color: #fff;
    border-radius: 10px;
    text-align: center;
    cursor: pointer;
}

.stateBox2:hover {
    background-color: #dcdcdc;
}

/* 在线 */
.state1 {
    display: inline-block;
    height: 10px;
    width: 10px;
    border-radius: 50%;
    background-color: #8ee80e;
}

/* 离线 */
.state2 {
    display: inline-block;
    height: 10px;
    width: 10px;
    border-radius: 50%;
    background-color: #cacaca;
}

/* 忙碌 */
.state3 {
    display: inline-block;
    height: 10px;
    width: 10px;
    border-radius: 50%;
    background-color: #ff8c1e;
}

/* 退出登录 */
.state4 {
    display: inline-block;
    height: 10px;
    width: 10px;
    border-radius: 50%;
    background-color: #7e7e7e;
}

.stateText {
    font-size: 14px;
    margin-left: 5px;
}

/* 列表和信息 */
.infoBox {
    height: 90%;
    width: 100%;
    display: flex;
}

/* 用户列表大盒子 */
.userList {
    height: 100%;
    width: 300px;
    border-right: 1px solid #ccc;
    display: flex;
    flex-direction: column;
}

/* 用户列表 */
.userListBox {
    flex: 1;
    width: 100%;
    overflow: auto;
}

/* 信息外层盒子 */
.infoList {
    height: 100%;
}

/* 信息列表 */
.infoTop {
    height: 70%;
    width: 100%;
    border-bottom: 1px solid #ccc;
    padding: 10px;
    box-sizing: border-box;
    overflow: auto;
    min-height: 200px;
    max-height: 500px;
}

/* 对方发的信息样式 */
.chatInfoLeft {
    min-height: 70px;
    margin-left: 10px;
    margin-top: 10px;
    display: flex;
}

.chatLeft {
    margin-left: 15px;
    flex: 1;
}

.chatLeft .text {
    color: #434343;
    margin-top: 8px;
    background-color: #e3e3e3;
    display: inline-block;
    padding: 6px 10px;
    border-radius: 10px;
    max-width: 50%;
    /* 忽略多余的空白，只保留一个空白 */
    white-space: normal;
    /* 换行显示全部字符 */
    word-break: break-all;
    white-space: pre-wrap;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

/* 自己发的信息样式 */
.chatInfoRight {
    height: 70px;
    margin-left: 10px;
    margin-top: 10px;
    display: flex;
    flex-direction: row-reverse;
}

.chatRight {
    margin-right: 15px;
    flex: 1;
    /* 用align-items把元素靠右对齐 */
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

.chatRight .text {
    color: #434343;
    margin-top: 8px;
    background-color: #95ec69;
    display: inline-block;
    padding: 6px 10px;
    border-radius: 10px;
    max-width: 50%;
    /* 忽略多余的空白，只保留一个空白 */
    white-space: normal;
    /* 换行显示全部字符 */
    word-break: break-all;
    white-space: pre-wrap;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.searchBox {
    padding: 4px 2px;
    border-bottom: 1px solid #ededed;
}

.input-with-select {
    width: 80%;
    margin-right: 2%;
}

/* 用户名 */
.userName {
    width: 100px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}


/* 滚动条样式 */
::-webkit-scrollbar {
    width: 5px;
    height: 10px;
}

::-webkit-scrollbar-thumb {
    background-color: #dbd9d9;
    border-radius: 3px;
}
</style>
