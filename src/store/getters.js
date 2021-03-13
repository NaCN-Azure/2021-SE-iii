const getters = {
  //admin
  managerList: state => state.admin.managerList,
  marketerList: state => state.admin.marketerList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addMarketerModalVisible: state => state.admin.addMarketerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  changeHotelManagerVisible: state => state.admin.changeHotelManagerVisible,
  curHotelId: state => state.admin.curHotelId,
  selectedManagerId: state => state.admin.selectedManagerId,
  reviseUsersInfoVisible: state => state.admin.reviseUsersInfoVisible,
  selectedUserId: state => state.admin.selectedUserId,
  reviseUsersInfoParams: state => state.admin.reviseUsersInfoParams,
  photoHotelId:state=>state.admin.photoHotelId,
  addHotelPhotoVisible:state=>state.admin.addHotelPhotoVisible,
  }
  
  export default getters