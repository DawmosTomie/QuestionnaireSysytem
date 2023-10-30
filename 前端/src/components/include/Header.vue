<template>
  <div>
    <v-app-bar :color="themeColor" flat dark>
      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" @click="$router.back()">
            <v-icon>mdi-arrow-left</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">返回上一级</span>
      </v-tooltip>

      <v-toolbar-title class="font-weight-bold">{{title}}</v-toolbar-title>

      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" v-show="$viewport .width > 450">
            <v-icon
              @click="isStarred ? isStarred = false : isStarred = true"
            >{{ isStarred ? 'mdi-star' : 'mdi-star-outline' }}</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">标记为模板</span>
      </v-tooltip>

      <div class="flex-grow-1"></div>

      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" @click.stop="drawer = !drawer">
            <v-icon>mdi-palette</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">自定义主题</span>
      </v-tooltip>

      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" v-show="$viewport .width > 450">
            <v-icon>mdi-eye</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">预览</span>
      </v-tooltip>

      <DialogSettings ref="setting" :themeColor="themeColor" @saveSettings="saveSettings"/>

      <DialogSend :themeColor="themeColor" />

      <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" @click="checkAddQuestion" v-show="$viewport .width > 450">
            <v-icon>mdi-content-save</v-icon>
          </v-btn>
        </template>
        <span class="tooltip">保存</span>
      </v-tooltip>
      <!-- 
      <v-menu
        left
        bottom
      >-->
      <!-- <template v-slot:activator="{ on: menu }">
          <v-tooltip bottom>
            <template v-slot:activator="{ on: tooltip }">
              <v-btn 
                icon 
                v-on="{ ...tooltip, ...menu }">
                  <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </template>
            <span class="tooltip">More</span>
          </v-tooltip>
      </template>-->

      <!-- <v-list>
          <v-list-item
            v-for="(option, index) in options"
            :key="index"
            @click="">
            <v-list-item-icon>
              <v-icon>{{ option.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{ option.name }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>-->
    </v-app-bar>

    <NavCustomizeThemeColor
      @changeThemeColor="(...args) => changeThemeColor(...args)"
      @changeBackgroundColor="(...args) => changeBackgroundColor(...args)"
      v-model="drawer"
    />
  </div>
</template>

<script>
import DialogSettings from '@/components/widgets/dialogs/DialogSettings'
import DialogSend from '@/components/widgets/dialogs/DialogSend'
import NavCustomizeThemeColor from '@/components/widgets/navs/NavCustomizeThemeColor'

export default {
  props:{
    title:{
      type:String,
      default:'未命名表单'
    }
  },
  components: {
    DialogSettings,
    DialogSend,
    NavCustomizeThemeColor,
  },
  mounted(){
    this.$refs.setting.question = this.questionnaire
  },
  data() {
    return {
      options: [
        { name: 'Undo', icon: 'mdi-undo' },
        { name: 'Make a copy', icon: 'mdi-content-copy' },
        { name: 'Move to trash', icon: 'mdi-delete' },
        { name: 'Get pre-filled link', icon: 'mdi-link' },
        { name: 'Print', icon: 'mdi-printer' },
        { name: 'Add collaborators', icon: 'mdi-account-multiple-plus' },
        { name: 'Script editor', icon: 'mdi-code-tags' },
        { name: 'Add-ons', icon: 'mdi-puzzle' },
        { name: 'Preferences', icon: 'mdi-account-settings' },
      ],
      isStarred: false,
      drawer: false,
      themeColor: 'blue lighten-1',
      questionnaire:{}
    }
  },
  methods: {
    saveSettings(val){
      this.$emit('saveSettings',val)
    },
    changeThemeColor(args) {
      this.themeColor = args
      this.$emit('changeThemeColor', args)
    },
    changeBackgroundColor(args) {
      this.$emit('changeBackgroundColor', args)
    },
    checkAddQuestion() {
      this.$emit('saveQuestion')
    },
  },
}
</script>

<style scoped>
.tooltip {
  font-size: 12px;
}
</style>
