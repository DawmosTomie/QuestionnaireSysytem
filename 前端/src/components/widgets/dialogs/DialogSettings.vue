<template>
  <v-dialog max-width="600px" v-model="dialog" :fullscreen="$viewport.width < 450">
    <template v-slot:activator="{ on: dialog }">
      <v-tooltip bottom>
        <template v-slot:activator="{ on: tooltip }">
          <v-btn icon v-on="{ ...tooltip, ...dialog }">
            <v-icon>mdi-cog-outline</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">设置</span>
      </v-tooltip>
    </template>
    <v-card tile>
      <v-card-title v-bind:style="{ backgroundColor: themeColorList[themeColor] }">
        <v-btn class="close-button" icon color="white" v-show="$viewport.width < 450" @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <span class="headline font-weight-medium dialog-title">设置</span>
        <div class="flex-grow-1"></div>
        <v-btn icon color="white" v-show="$viewport.width < 450" @click="dialog = false">保存</v-btn>
      </v-card-title>
      <v-tabs :background-color="themeColor" color="white" slider-color="white">
        <v-tabs-slider></v-tabs-slider>
        <v-tab>通用</v-tab>
        <v-tab>显示</v-tab>
        <!-- <v-tab>测试</v-tab> -->
        <v-tab-item>
          <v-card-text class="text--primary">
            <v-row>
              <v-col>
                <span class="font-weight-medium">基础设置</span>
                <v-text-field type="number" :color="themeColor" label="答卷次数上限" hide-details single-line/>
                <div>
                  <v-menu class="datepicker-menu" ref="menu" v-model="show_start_datepicker"
                    :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field class="mr-4 mt-5" style="width: 200px; flex: none; background: none"
                        v-model="question.startTime" placeholder="开始时间" append-icon="mdi-calendar-clock" readonly
                        rows="1" v-bind="attrs" v-on="on"></v-text-field>
                    </template>
                    <v-date-picker v-model="question.startTime" :allowed-dates="allowedStartDate" no-title scrollable
                      :show-current="false" @input="show_start_datepicker = false"
                      :day-format="date => date.split('-')[2]" locale="zh-cn">
                    </v-date-picker>
                  </v-menu>
                  <v-menu class="datepicker-menu" ref="menu" v-model="show_end_datepicker"
                    :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field class="mr-4 mt-5" style="width: 200px; flex: none; background: none"
                        v-model="question.endTime" placeholder="结束时间" append-icon="mdi-calendar-clock" readonly rows="1"
                        v-bind="attrs" v-on="on"></v-text-field>
                    </template>
                    <v-date-picker v-model="question.endTime" :allowed-dates="allowedEndDate" no-title scrollable
                      :show-current="false" @input="show_end_datepicker = false"
                      :day-format="date => date.split('-')[2]" locale="zh-cn">
                    </v-date-picker>
                  </v-menu>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <span class="font-weight-medium">必须登录作答:</span>
                <v-checkbox :color="themeColor" label="只能作答一次"></v-checkbox>
              </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row>
              <v-col>
                <span class="font-weight-medium">答者操作:</span>
                <v-checkbox :color="themeColor" label="可以多次提交"></v-checkbox>
                <v-checkbox :color="themeColor" label="查看其他人答卷"></v-checkbox>
              </v-col>
            </v-row>
            <v-divider></v-divider>
            <!-- <v-row>
              <v-col cols="12">
                <span class="font-weight-medium">Maximum size of all files uploaded:</span>
              </v-col>
              <v-col cols="4">
                <v-select 
                  :items="maximumSizeOfFiles"
                  :label="selectedMax">
                </v-select>
              </v-col>
            </v-row> -->
          </v-card-text>
        </v-tab-item>
        <v-tab-item>
          <v-card-text class="text--primary">
            <v-row>
              <v-col>
                <v-checkbox :color="themeColor" label="显示回答进度">
                </v-checkbox>
                <v-checkbox :color="themeColor" label="打乱问题顺序">
                </v-checkbox>
                <v-checkbox :color="themeColor" label="回答完成后跳转至奖励界面" v-model="question.jump">
                </v-checkbox>
                <!-- <p class="font-weight-bold">Confirmation message:</p>
              <p>Your response has been recorded.</p> -->
                <v-divider></v-divider>
              </v-col>
            </v-row>
          </v-card-text>
        </v-tab-item>
        <!-- <v-tab-item>
        <v-card-text>
          <v-row>
            <v-col>
              <v-switch 
                class="switch"
                :color="themeColor"
                label="Make this a quiz"
                v-model="isQuiz"></v-switch>
              <span>Assign point values to questions and allow auto-grading.</span>        
            </v-col>
          </v-row>
          <v-divider></v-divider>
          <v-row>
            <v-col>
              <h3 class="font-weight-bold">表单设置</h3>
              <br>
              <p class="font-weight-bold">Release grade:</p>
              <v-radio-group 
                v-model="active"
                :disabled="!isQuiz">
                <v-radio
                  label="Immediately after each submission"
                  :value="true"
                  :color="themeColor"></v-radio>
                <v-radio
                  label="Later, after manual review"
                  :value="false"
                  :color="themeColor"></v-radio>
              </v-radio-group>
              <p class="font-weight-bold">答者可见:</p>
              <v-checkbox
                :color="themeColor"
                label="Missed questions"
                append-icon="mdi-help-circle"
                v-model="checkbox2"
                :disabled="!isQuiz">
              </v-checkbox>
              <v-checkbox
                :color="themeColor"
                label="Correct answers"
                append-icon="mdi-help-circle"
                v-model="checkbox3"
                :disabled="!isQuiz">
              </v-checkbox>
              <v-checkbox
                :color="themeColor"
                label="Point values"
                append-icon="mdi-help-circle"
                v-model="checkbox4"
                :disabled="!isQuiz">
              </v-checkbox> 
            </v-col>
          </v-row>
        </v-card-text>
      </v-tab-item> -->
      </v-tabs>
      <v-divider></v-divider>
      <v-card-actions v-show="$viewport.width > 450">
        <div class="flex-grow-1"></div>
        <v-btn text @click="dialog = false">取消</v-btn>
        <v-btn :color="themeColor" text @click="saveSettings">保存</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: ["themeColor"],
  data() {
    return {
      question: {},
      show_start_datepicker: false,
      show_end_datepicker: false,
      dialog: false,
      active: true,
      checkbox1: true,
      checkbox2: true,
      checkbox3: true,
      checkbox4: true,
      isQuiz: false,
      isEmailCollected: false,
      selectedMax: "1GB",
      maximumSizeOfFiles: ['1GB', '10GB', '100GB', '1TB'],
      themeColorList: {
        'red darken-1': '#E53935',
        'deep-purple darken-1': '#5E35B1',
        'indigo darken-1': '#3949AB',
        'blue darken-1': '#1E88E5',
        'light-blue darken-1': '#039BE5',
        'cyan darken-1': '#00ACC1',
        'deep-orange darken-1': '#F4511E',
        'orange darken-1': '#FB8C00',
        'teal darken-1': '#00897B',
        'green darken-1': '#43A047',
        'blue-grey darken-1': '#546E7A',
        'grey darken-1': '#757575',
      },
    }
  },
  methods: {
    saveSettings() {
      this.dialog = false
      this.$emit('saveSettings', this.question)
    },
    allowedStartDate(val) {
      if (this.question.endTime) {
        let cur = new Date(val).getTime();
        let end = new Date(this.question.endTime).getTime();

        return cur < end;
      }
      return true;
    },
    allowedEndDate(val) {
      if (this.question.startTime) {
        let cur = new Date(val).getTime();
        let end = new Date(this.question.startTime).getTime();
        return cur > end;
      }
      return true;
    }
  }
}
</script>

<style scoped>
.dialog-title {
  color: #FFFFFF;
}

.close-button {
  padding-right: 30px;
}
</style>