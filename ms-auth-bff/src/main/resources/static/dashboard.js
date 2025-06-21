// Productos del catálogo
const products = [
    {
        id: 1,
        name: "Taladro Eléctrico Profesional",
        description: "Potencia y durabilidad para trabajos exigentes",
        price: 89990,
        category: "herramientas",
        icon: "fas fa-tools"
    },
    {
        id: 2,
        name: "Set de Herramientas Básicas",
        description: "Todo lo necesario para el hogar",
        price: 45990,
        category: "herramientas",
        icon: "fas fa-wrench"
    },
    {
        id: 3,
        name: "Cable Eléctrico 2.5mm",
        description: "Calidad premium para instalaciones",
        price: 12990,
        category: "electricidad",
        icon: "fas fa-bolt"
    },
    {
        id: 4,
        name: "Cemento Portland 25kg",
        description: "Material de construcción de alta calidad",
        price: 8990,
        category: "construccion",
        icon: "fas fa-hammer"
    },
    {
        id: 5,
        name: "Tubería PVC 2 pulgadas",
        description: "Para sistemas de agua y drenaje",
        price: 15990,
        category: "plomeria",
        icon: "fas fa-faucet"
    },
    {
        id: 6,
        name: "Pintura Interior Premium",
        description: "Cobertura excelente y fácil aplicación",
        price: 25990,
        category: "pintura",
        icon: "fas fa-paint-brush"
    },
    {
        id: 7,
        name: "Cortadora de Césped Manual",
        description: "Herramienta esencial para el jardín",
        price: 18990,
        category: "jardin",
        icon: "fas fa-seedling"
    },
    {
        id: 8,
        name: "Sierra Circular Eléctrica",
        description: "Precisión y potencia para cortes perfectos",
        price: 129990,
        category: "herramientas",
        icon: "fas fa-tools"
    },
    {
        id: 9,
        name: "Interruptor Simple",
        description: "Control de iluminación confiable",
        price: 3990,
        category: "electricidad",
        icon: "fas fa-bolt"
    },
    {
        id: 10,
        name: "Ladrillos Refractarios",
        description: "Resistentes al calor y durables",
        price: 15990,
        category: "construccion",
        icon: "fas fa-hammer"
    },
    {
        id: 11,
        name: "Válvula de Agua 1 pulgada",
        description: "Control preciso del flujo de agua",
        price: 8990,
        category: "plomeria",
        icon: "fas fa-faucet"
    },
    {
        id: 12,
        name: "Rodillo de Pintura Profesional",
        description: "Aplicación uniforme y eficiente",
        price: 5990,
        category: "pintura",
        icon: "fas fa-paint-brush"
    }
];

// Variables globales
let currentCategory = 'all';
let currentSearch = '';

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    loadUserInfo();
    loadProducts();
    setupEventListeners();
});

// Cargar información del usuario
function loadUserInfo() {
    const userData = localStorage.getItem('userData');
    if (userData) {
        const user = JSON.parse(userData);
        document.getElementById('userName').textContent = user.username;
        document.getElementById('userGreeting').textContent = user.username;
    }
}

// Configurar event listeners
function setupEventListeners() {
    // Búsqueda
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', function(e) {
        currentSearch = e.target.value.toLowerCase();
        filterProducts();
    });
}

// Cargar productos
function loadProducts() {
    const productsGrid = document.getElementById('productsGrid');
    productsGrid.innerHTML = '';
    
    products.forEach(product => {
        const productCard = createProductCard(product);
        productsGrid.appendChild(productCard);
    });
}

// Crear tarjeta de producto
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card';
    card.innerHTML = `
        <div class="product-image">
            <i class="${product.icon}"></i>
        </div>
        <div class="product-content">
            <h4>${product.name}</h4>
            <p>${product.description}</p>
            <div class="product-price">$${product.price.toLocaleString()}</div>
            <button class="btn-buy" onclick="showProductDetails(${product.id})">
                Ver Detalles
            </button>
        </div>
    `;
    return card;
}

// Filtrar productos por categoría
function filterByCategory(category) {
    currentCategory = category;
    filterProducts();
    
    // Actualizar estado visual de las categorías
    document.querySelectorAll('.category-card').forEach(card => {
        card.style.borderColor = 'transparent';
    });
    
    if (category !== 'all') {
        event.target.closest('.category-card').style.borderColor = '#667eea';
    }
}

// Filtrar productos
function filterProducts() {
    const productsGrid = document.getElementById('productsGrid');
    productsGrid.innerHTML = '';
    
    const filteredProducts = products.filter(product => {
        const matchesCategory = currentCategory === 'all' || product.category === currentCategory;
        const matchesSearch = product.name.toLowerCase().includes(currentSearch) || 
                             product.description.toLowerCase().includes(currentSearch);
        return matchesCategory && matchesSearch;
    });
    
    if (filteredProducts.length === 0) {
        productsGrid.innerHTML = `
            <div style="grid-column: 1 / -1; text-align: center; padding: 3rem;">
                <i class="fas fa-search" style="font-size: 3rem; color: #a0aec0; margin-bottom: 1rem;"></i>
                <h3 style="color: #718096;">No se encontraron productos</h3>
                <p style="color: #a0aec0;">Intenta con otros filtros o términos de búsqueda</p>
            </div>
        `;
        return;
    }
    
    filteredProducts.forEach(product => {
        const productCard = createProductCard(product);
        productsGrid.appendChild(productCard);
    });
}

// Mostrar detalles del producto
function showProductDetails(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;
    
    // Crear modal con detalles del producto
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>${product.name}</h3>
                <button class="modal-close" onclick="closeModal()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="product-detail-image">
                    <i class="${product.icon}"></i>
                </div>
                <div class="product-detail-info">
                    <p class="product-description">${product.description}</p>
                    <div class="product-price-large">$${product.price.toLocaleString()}</div>
                    <div class="product-features">
                        <h4>Características:</h4>
                        <ul>
                            <li>Calidad premium</li>
                            <li>Garantía de 1 año</li>
                            <li>Envío gratis</li>
                            <li>Devolución en 30 días</li>
                        </ul>
                    </div>
                    <div class="product-actions">
                        <button class="btn-primary" onclick="addToCart(${product.id})">
                            <i class="fas fa-shopping-cart"></i>
                            Agregar al Carrito
                        </button>
                        <button class="btn-secondary" onclick="contactUs()">
                            <i class="fas fa-phone"></i>
                            Consultar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    // Agregar estilos del modal
    const style = document.createElement('style');
    style.textContent = `
        .modal {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 1000;
        }
        .modal-content {
            background: white;
            border-radius: 15px;
            max-width: 600px;
            width: 90%;
            max-height: 90vh;
            overflow-y: auto;
        }
        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1.5rem;
            border-bottom: 1px solid #e2e8f0;
        }
        .modal-close {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: #a0aec0;
        }
        .modal-body {
            padding: 1.5rem;
            display: flex;
            gap: 2rem;
        }
        .product-detail-image {
            width: 150px;
            height: 150px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 3rem;
        }
        .product-detail-info {
            flex: 1;
        }
        .product-price-large {
            font-size: 2rem;
            font-weight: 700;
            color: #667eea;
            margin: 1rem 0;
        }
        .product-features ul {
            list-style: none;
            padding: 0;
        }
        .product-features li {
            padding: 0.5rem 0;
            color: #718096;
        }
        .product-features li:before {
            content: "✓";
            color: #667eea;
            font-weight: bold;
            margin-right: 0.5rem;
        }
        .product-actions {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
        }
        .btn-primary, .btn-secondary {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-secondary {
            background: #e2e8f0;
            color: #2d3748;
        }
        .btn-primary:hover, .btn-secondary:hover {
            transform: translateY(-2px);
        }
    `;
    
    document.head.appendChild(style);
    document.body.appendChild(modal);
}

// Cerrar modal
function closeModal() {
    const modal = document.querySelector('.modal');
    if (modal) {
        modal.remove();
    }
}

// Agregar al carrito
function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    if (!product) return;
    
    // Simular agregar al carrito
    showNotification(`¡${product.name} agregado al carrito!`, 'success');
    closeModal();
}

// Contactar
function contactUs() {
    showNotification('¡Te contactaremos pronto!', 'info');
    closeModal();
}

// Mostrar notificación
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    // Estilos de notificación
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 2rem;
        border-radius: 8px;
        color: white;
        font-weight: 500;
        z-index: 1001;
        animation: slideIn 0.3s ease;
    `;
    
    if (type === 'success') {
        notification.style.background = '#48bb78';
    } else if (type === 'error') {
        notification.style.background = '#f56565';
    } else {
        notification.style.background = '#4299e1';
    }
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Cerrar sesión
// ...existing code...
function logout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userData');
    window.location.href = '/logout.html'; // Redirige a la página de logout
}
// ...existing code...

// Animación para notificaciones
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
`;
document.head.appendChild(style); 