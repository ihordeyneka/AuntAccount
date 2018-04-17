define(["../core/config"], function(config) {

  var swRegistration = null;
  //TODO: receive public key from server
  const applicationServerPublicKey = urlB64ToUint8Array('BBHf1J50o1qRq_QPs_y-Lpg21Jo9hfMZlKjbO7sPJ-GxfjCS7qC9joosXVZrs6rM8sARigM7HgJGWG6beRmT4d0');

  if ('serviceWorker' in navigator && 'PushManager' in window) {
      console.log('Service Worker and Push is supported');

      navigator.serviceWorker.register('sw.js')
          .then(function(swReg) {
              console.log('Service Worker is registered', swReg);

              swRegistration = swReg;
          })
          .catch(function(error) {
              console.error('Service Worker Error', error);
          });
  } else {
      console.warn('Push messaging is not supported');
  }

  navigator.serviceWorker.ready.then(function(serviceWorkerRegistration) {
      serviceWorkerRegistration.pushManager.subscribe({
              userVisibleOnly: true,
              applicationServerKey: applicationServerPublicKey
          })
          .then(function(subscription) {
              console.log('User is subscribed.');
              updateSubscriptionOnServer(subscription);
          })
          .catch(function(e) {
              console.log('Something unfortunate happened: ' + e);
          });
  });

 function urlB64ToUint8Array(base64String) {
      const padding = '='.repeat((4 - base64String.length % 4) % 4);
      const base64 = (base64String + padding)
          .replace(/\-/g, '+')
          .replace(/_/g, '/');

      const rawData = window.atob(base64);
      const outputArray = new Uint8Array(rawData.length);

      for (var i = 0; i < rawData.length; ++i) {
          outputArray[i] = rawData.charCodeAt(i);
      }
      return outputArray;
  }

  function updateSubscriptionOnServer(subscription) {

      if (subscription) {
          var subscriptionJSON = subscription.toJSON()
          var subscriptionData = {
            endpoint: subscriptionJSON.endpoint,
            publicKey: subscriptionJSON.keys.p256dh,
            auth: subscriptionJSON.keys.auth,
            userId: $.didoauth.user.id
          };

          $.post({
              url: config.apiRoot + "/subscriptions",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(subscriptionData)
          }).done(function(data) {
           console.log('User is subscribed.');
          }).fail(function(result) {
           console.log('User is not subscribed.');
          });

      }
  }

});