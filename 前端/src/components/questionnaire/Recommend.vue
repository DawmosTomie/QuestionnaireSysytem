<template>
  <div>
    <template v-for="(item, index) in surveyCrowds">
      <el-badge
        :value="item.tempNum"
        size="small"
        type="primary"
        :max="99"
        class="item"
        @click.native="queryTemplates(item.name)"
      >{{ item.name }}</el-badge>
    </template>
    <div>
      <el-row>
        <el-col
          :span="5"
          v-for="(item) in curCrowdTemplates"
          :key="item.questionnaireId"
          :offset="1"
        >
          <div style="margin-top:15px;width: 100%;">
            <el-badge :value="item.childNum" :max="99" style="width:100%;">
              <el-card :body-style="{ padding: '10px' }" shadow="always" style="height: 350px;">
                <div slot="header" style="height:170px">
                  <div class="details-title">{{ item.title }}</div>
                  <div
                    style="font-size: 14px;color: #A6A6A6;line-height: 20px;margin-bottom: 15px;"
                  >
                    <span>{{ item.desc ? item.desc : '暂无描述' }}</span>
                  </div>
                </div>
                <img
                  style="width: 90px; height: 90px"
                  src="../../assets/images/type-diaocha.png"
                  :fit="fit"
                ></img>
                <div style="height: 20px;">
                  <div class="bottom clearfix">
                    <el-button
                      type="text"
                      class="button"
                      @click="previewWj(item.questionnaireId)"
                    >预览</el-button>
                    <el-button
                      type="text"
                      class="button"
                      @click="dialogShow = true; curTemp = item;"
                    >使用模板</el-button>
                  </div>
                </div>
              </el-card>
            </el-badge>
          </div>
        </el-col>
      </el-row>
    </div>

    <!--添加问卷弹窗-->
    <el-dialog title="添加问卷" :visible.sync="dialogShow" :close-on-click-modal="false" class="dialog">
      <el-form ref="form" :model="willAddWj" label-width="80px">
        <el-form-item label="问卷标题" style="width: 100%;" required>
          <el-input v-model="willAddWj.title" placeholder="请输入问卷标题"></el-input>
        </el-form-item>
        <el-form-item label="问卷描述" style="width: 100%;">
          <el-input v-model="willAddWj.desc" type="textarea" placeholder="请输入问卷描述" rows="5"></el-input>
        </el-form-item>
        <el-form-item label="调查人群" style="width: 100%;">
          <el-input v-model="willAddWj.surveyCrowd" placeholder="请输入调查人群"></el-input>
        </el-form-item>
        <el-form-item label="是否添加为模板">
          <el-switch v-model="willAddWj.isTemplate" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="有效期" label-width="80px">
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-date-picker
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="开始日期"
                v-model="willAddWj.startTime"
                style="width: 100%;"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-date-picker
                placeholder="结束日期"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                v-model="willAddWj.endTime"
                style="width: 100%;"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
      </el-form>
      <div style="width: 100%;text-align: right">
        <el-button style="margin-left: 10px;" @click="dialogShow = false">取消</el-button>
        <el-button type="primary" style="margin-left: 10px;" @click="useTemp">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import backImg from '@/assets/images/type-diaocha.png'
export default {
  data() {
    return {
      surveyCrowds: [],
      curCrowdTemplates: [],
      dialogShow: false,
      curTemp: {},
      willAddWj: {
        id: 0,
        title: '',
        flag: 0,
        desc: '感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！',
      },
    }
  },
  methods: {
    queryTemplates(crowdName) {
      this.$post('/api/questionnaire/querySurveyCrowd', {
        surveyCrowd: crowdName,
      }).then((res) => {
        this.curCrowdTemplates = res
      })
    },
    previewWj(questionnaireId) {
      let url = window.location.origin + '/display/' + questionnaireId //问卷链接
      window.open(url)
    },

    useTemp() {
      this.$post('/api/questionnaire/useTemplate', {
        ...this.willAddWj,
        questionnaireId: this.curTemp.questionnaireId,
      })
        .then((data) => {
          this.$message({
            type: 'success',
            message: '使用模板成功！',
            showClose: true,
          })
          this.dialogShow = false
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          })
        })
    },
  },
  mounted() {
    this.$post('/api/questionnaire/queryTemplateSurveyCrowd',{}).then(
      (res) => {
        console.log(res.data)
        this.surveyCrowds = res
        if(res&&res.length>0){
            this.queryTemplates(res[0].name)
        }
      }
    )
  },
}
</script>
<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
}

.bottom {
  float:right;
  padding-bottom: 13px;
  line-height: 12px;
}


.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}

.clearfix:after {
  clear: both;
}
.details-title {
  font-size: 18px;
  font-weight: 600;
  color: #262626;
  line-height: 24px;
  padding: 36px 0 19px 0;
}
</style>
