/** An empty service worker! */
self.addEventListener('fetch', function(event) {
  /** An empty fetch handler! */
});

//TODO show message
self.addEventListener('push', function(event) {
  console.log('[Service Worker] Push Received.');
  console.log('[Service Worker] Push had this data: "${event.data.text()}"');

  const title = 'DIDO';
  const options = {
    body: event.data.text()
  };

  event.waitUntil(self.registration.showNotification(title, options));
});

//TODO: redirect to message with ID
self.addEventListener('notificationclick', function(event) {
  console.log('[Service Worker] Notification click Received.');

  event.notification.close();

  event.waitUntil(
    clients.openWindow('http://localhost:8282/#post')
  );
});