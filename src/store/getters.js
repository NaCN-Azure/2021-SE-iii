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

  //graph
  domain: state => state.graph.domain,
  domainList: state => state.graph.domainList,
  nodeList: state => state.graph.nodeList,
  linkList: state => state.graph.linkList,
  shapes: state => state.graph.shapes,
  createNodeParams: state => state.graph.createNodeParams,
  updateNodeParams: state => state.graph.updateNodeParams,
  createLinkParams: state => state.graph.createLinkParams,
  updateLinkParams: state => state.graph.updateLinkParams,
  addDomainParams: state => state.graph.addDomainParams,
  updateNodeDialogVisible: state => state.graph.updateNodeDialogVisible,
  updateLinkDialogVisible: state => state.graph.updateLinkDialogVisible,
  createNodeDialogVisible: state => state.graph.createNodeDialogVisible,
  createLinkDialogVisible: state => state.graph.updateLinkDialogVisible,
  addDomainDialogVisible: state => state.graph.addDomainDialogVisible,
  }
  
  export default getters