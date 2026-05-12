const CACHE_NAME = 'kisan-saathi-v1';
const urlsToCache = [
 './',
 './index.html',
 './crop-health.html',
 './weather.html',
 './profit.html',
 './voice.html',
 './sowing.html',

 './js/voice-engine.js',
 './js/api-service.js',
 './js/speech-service.js',
 './js/offline-cache.js',
 './js/ui-manager.js',

 './css/style.css',

 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'
];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => cache.addAll(urlsToCache))
  );
});

self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request).then(response => {
      event.respondWith(
  caches.match(event.request).then(cached => {
    const fetchPromise = fetch(event.request)
      .then(networkResponse => {
        caches.open(CACHE_NAME).then(cache => {
          cache.put(event.request, networkResponse.clone());
        });
        return networkResponse;
      });

    return cached || fetchPromise;
  })
);
    })
  );
});




