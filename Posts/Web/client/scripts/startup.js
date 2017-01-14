requirejs.config({
  baseUrl: '/client/scripts',
  map: {
    '*': {
      'communication_client': 'communication/mock_client'
    }
  }
});
