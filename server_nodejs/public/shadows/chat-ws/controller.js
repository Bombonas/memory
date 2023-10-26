class ChatWS extends HTMLElement {
    constructor() {
        super()
        this.shadow = this.attachShadow({ mode: 'open' })
        this.view = "chat-view-disconnected"
    }

    async connectedCallback() {
        // Carrega els estils CSS
        const style = document.createElement('style')
        style.textContent = await fetch('/shadows/chat-ws/style.css').then(r => r.text())
        this.shadow.appendChild(style)
    
        // Carrega els elements HTML
        const htmlContent = await fetch('/shadows/chat-ws/view.html').then(r => r.text())

        // Converteix la cadena HTML en nodes utilitzant un DocumentFragment
        const template = document.createElement('template');
        template.innerHTML = htmlContent;
        
        // Clona i afegeix el contingut del template al shadow
        this.shadow.appendChild(template.content.cloneNode(true));
    } 

    async showView (viewName) {
        // Amagar totes les vistes
        let animTime = '500ms';
        let refDisconnected = this.shadow.querySelector('chat-view-disconnected').shadowRoot.querySelector('.root')
        let refConnecting = this.shadow.querySelector('chat-view-connecting').shadowRoot.querySelector('.root')
        let refDisconnecting = this.shadow.querySelector('chat-view-disconnecting').shadowRoot.querySelector('.root')
        let refConnected = this.shadow.querySelector('chat-view-connected').shadowRoot.querySelector('.root')

        // Mostrar la vista seleccionada, amb l'status indicat
        switch (viewName) {
        case 'viewDisconnected':
            if (this.view == 'viewConnecting') {
                this.animateViewChange('right', animTime, refConnecting, refDisconnected)
            }
            if (this.view == 'viewDisconnecting') {
                this.animateViewChange('right', animTime, refDisconnecting, refDisconnected)
            }
            break
        case 'viewConnecting':
            this.animateViewChange('left', animTime, refDisconnected, refConnecting)
            break
        case 'viewDisconnecting':
            this.animateViewChange('right', animTime, refConnected, refDisconnecting)
            break
        case 'viewConnected':
            this.animateViewChange('left', animTime, refConnecting, refConnected)
            break
        }
        this.view = viewName
    }

    async animateViewChange (type, animTime, view0, view1) {
        if (type == 'right') {
            await Promise.all([
                this.animateElement(view0, animTime, "translate3d(0, 0, 0)", "translate3d(100%, 0, 0)"),
                this.animateElement(view1, animTime, "translate3d(-100%, 0, 0)", "translate3d(0%, 0, 0)")
            ])
        } else {
            await Promise.all([
                this.animateElement(view0, animTime, "translate3d(0, 0, 0)", "translate3d(-100%, 0, 0)"),
                this.animateElement(view1, animTime, "translate3d(100%, 0, 0)", "translate3d(0%, 0, 0)")
            ])
        }
    }

    async animateElement(element, animTime, posBegin, posEnd) {
        element.style.display = 'flex';
        element.style.transition = 'none';
    
        element.style.transform = posBegin;
    
        // Add small delay to ensure element is positioned before animating
        setTimeout(() => {
            let transition = 'transform ' + animTime + ' ease 0s';
            element.style.transition = transition;
    
            element.style.transform = posEnd;
        }, 100);
    }
}

// Defineix l'element personalitzat
customElements.define('chat-ws', ChatWS)