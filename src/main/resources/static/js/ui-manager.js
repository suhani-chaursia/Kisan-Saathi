// js/ui-manager.js
function addMessage(text, type) {
    const container = document.getElementById('chatContainer');
    const div = document.createElement('div');
    div.className = `d-flex ${type === 'user' ? 'justify-content-end' : 'justify-content-start'}`;
    
    div.innerHTML = `
        <div class="chat-bubble ${type === 'user' ? 'user-bubble' : 'bot-bubble'}">
            ${text}
        </div>
    `;
    container.appendChild(div);
    container.scrollTop = container.scrollHeight;
}

function showLoading() {
    const container = document.getElementById('chatContainer');
    const id = 'loading-' + Date.now();
    container.innerHTML += `
        <div id="${id}" class="d-flex justify-content-start">
            <div class="chat-bubble bot-bubble">
                <i class="fas fa-spinner fa-spin"></i> सोच रहा हूँ...
            </div>
        </div>
    `;
    container.scrollTop = container.scrollHeight;
    return id;
}

function removeLoading(id) {
    const element = document.getElementById(id);
    if (element) element.remove();
}

// Status Update
function updateStatus(isOnline) {
    const statusEl = document.getElementById('onlineStatus');
    if (isOnline) {
        statusEl.className = "badge bg-success";
        statusEl.textContent = "🟢 Online";
    } else {
        statusEl.className = "badge bg-danger";
        statusEl.textContent = "🔴 Offline";
    }
}