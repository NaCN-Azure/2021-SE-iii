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

  // editor
  selectedDomain: state => state.editor.selectedDomain,
  createNodeDialogVisible: state => state.editor.createNodeDialogVisible,
  createNodeParams: state => state.editor.createNodeParams,
  shapes: state => state.editor.shapes,
  domainList: state => state.editor.domainList,
  editNodeDialogVisible: state => state.editor.editNodeDialogVisible,
  editNodeParams: state => state.editor.editNodeParams,
  relationships: state => state.editor.relationships,
  addDomainDialogVisible: state => state.editor.addDomainDialogVisible,
  createLinkDialogVisible: state => state.editor.createLinkDialogVisible,
  createLinkParams: state => state.editor.createLinkParams,
  editLinkDialogVisible: state => state.editor.editLinkDialogVisible,
  editLinkParams: state => state.editor.editLinkParams,
  nodesData: state => state.editor.nodesData,
  linksData: state => state.editor.linksData,
}

  export default getters
