const getters = {
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
  nodeListVisible: state => state.editor.nodeListVisible,
  nodePositions: state => state.editor.nodePositions,
  mode: state => state.editor.mode,
  options: state => state.editor.options,
  value: state => state.editor.value,

  // user
  isLogin: state => state.user.isLogin,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  modifyUserInfoParams: state => state.user.modifyUserInfoParams,
}

  export default getters
