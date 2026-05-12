// ==================== OFFLINE CACHE MANAGER ====================
const OfflineCache = {
    DB_NAME: 'KisanSaathiDB',
    DB_VERSION: 1,
    STORE_NAME: 'analysis',

    async initDB() {
        return new Promise((resolve, reject) => {
            const request = indexedDB.open(this.DB_NAME, this.DB_VERSION);
            
            request.onupgradeneeded = (event) => {
                const db = event.target.result;
                if (!db.objectStoreNames.contains(this.STORE_NAME)) {
                    db.createObjectStore(this.STORE_NAME, { keyPath: 'id' });
                }
            };

            request.onsuccess = () => resolve(request.result);
            request.onerror = () => reject(request.error);
        });
    },

    async save(key, data, expiryMinutes = 1440) { // 24 hours default
        try {
            const db = await this.initDB();
            const tx = db.transaction(this.STORE_NAME, 'readwrite');
            const store = tx.objectStore(this.STORE_NAME);

            const record = {
                id: key,
                data: data,
                timestamp: Date.now(),
                expiresAt: Date.now() + (expiryMinutes * 60 * 1000)
            };

            await store.put(record);
            console.log(`✅ Cached: ${key}`);
        } catch (e) {
            // Fallback to localStorage
            try {
                localStorage.setItem('kisan_' + key, JSON.stringify({
                    data, timestamp: Date.now(), expiresAt: Date.now() + (expiryMinutes * 60 * 1000)
                }));
            } catch (err) { console.warn("Cache failed", err); }
        }
    },

    async get(key) {
        try {
            const db = await this.initDB();
            const tx = db.transaction(this.STORE_NAME, 'readonly');
            const store = tx.objectStore(this.STORE_NAME);
            const record = await store.get(key);

            if (record && record.expiresAt > Date.now()) {
                return record.data;
            } else if (record) {
                await this.delete(key); // expired
            }
        } catch (e) {
            // Fallback to localStorage
            try {
                const item = localStorage.getItem('kisan_' + key);
                if (item) {
                    const parsed = JSON.parse(item);
                    if (parsed.expiresAt > Date.now()) return parsed.data;
                    else localStorage.removeItem('kisan_' + key);
                }
            } catch (err) {}
        }
        return null;
    },

    async delete(key) {
        try {
            const db = await this.initDB();
            const tx = db.transaction(this.STORE_NAME, 'readwrite');
            await tx.objectStore(this.STORE_NAME).delete(key);
        } catch (e) {}
    },

    isOnline() {
        return navigator.onLine;
    },

    // Clear old cache
    async clearOldCache() {
        try {
            const db = await this.initDB();
            const tx = db.transaction(this.STORE_NAME, 'readwrite');
            const store = tx.objectStore(this.STORE_NAME);
            const all = await store.getAll();

            for (let item of all) {
                if (item.expiresAt < Date.now()) {
                    await store.delete(item.id);
                }
            }
        } catch (e) {}
    }
};

// Auto cleanup on load
window.addEventListener('load', () => {
    OfflineCache.clearOldCache();
});

// Network Status
function updateNetworkStatus() {
    const badge = document.getElementById('offlineBadge');
    if (badge) {
        badge.classList.toggle('d-none', navigator.onLine);
    }
}

window.addEventListener('online', updateNetworkStatus);
window.addEventListener('offline', updateNetworkStatus);