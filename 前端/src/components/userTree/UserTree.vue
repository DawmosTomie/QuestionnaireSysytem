<template>
  <div>
    <el-tree
      :props="props"
      :data="userList"
      ref="userTree"
      show-checkbox
      @check-change="handleCheckChange"
    ></el-tree>
  </div>
</template>
<script>
export default {
  props: {
    grpId: Number,
  },
  data() {
    return {
      props: {
        label: 'username',
      },
      userList: [{ username: 'Joe' }, { username: 'IW' }],
      count: 1,
    }
  },
  mounted() {
    this.$post('/api/respondent/queryNoGrpUser').then((res) => {
      this.userList = res
    })
  },
  methods: {
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate)
    },
    handleNodeClick(data) {
      console.log(data)
    },
  },
}
</script>
