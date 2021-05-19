import { createRouter, createWebHashHistory } from 'vue-router'
import Community from '../views/Community.vue'
import CommunityEdit from '../views/CommunityEdit.vue'
import CommunityDetail from '../views/CommunityDetail.vue'
import MemberDetail from '../views/MemberDetail.vue'
import MemberEdit from '../views/MemberEdit.vue'
import Member from '../views/Member.vue'
import Record  from '../views/Record.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/community'
  },
  {
    path: '/community',
    name: 'Community',
    meta: {
      title: '社区管理'
    },
    component: Community
  },
  {
    path: '/community-edit',
    name: 'CommunityEdit',
    meta: {
      title: '编辑社区信息'
    },
    component: CommunityEdit
  },
  {
    path: '/community-detail',
    name: 'CommunityDetail',
    meta:{
      title: '社区详细信息'
    },
    component: CommunityDetail
  },
  {
    path: '/member-detail',
    name: 'MemberDetail',
    meta: {
      title: '居民详情'
    },
    component: MemberDetail
  },
  {
    path: '/member-edit',
    name: 'MemberEdit',
    meta:{
      title: '编辑居民信息'
    },
    component: MemberEdit
  },
  {
    path: '/member',
    name: 'Member',
    meta: {
      title: '居民管理'
    },
    component: Member
  },
  {
    path: '/record',
    name: 'Record',
    meta: {
      title: '记录管理'
    },
    component: Record
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
