const confirmation = {
    modal: new bootstrap.Modal(document.getElementById('confirmation_modal'), {backdrop: 'static', keyboard: false}),
    txtTitle: document.getElementById('confirmation_txtTitle'),
    txtText: document.getElementById('confirmation_txtText'),
    btnConfirm: document.getElementById('confirmation_btnConfirm'),
    btnCancel: document.getElementById('confirmation_btnCancel'),
    show: function (title, text, form, modal) {
        if (modal) modal.hide()
        this.btnConfirm.setAttribute('form', form.getAttribute('id'))
        this.btnConfirm.onclick = this.btnCancel.onclick = () => {
            this.modal.hide()
            if (modal) modal.show()
        }
        this.txtTitle.innerText = title
        this.txtText.innerText = text
        this.modal.show()
    },
}

const notification = {
    toast: new bootstrap.Toast(document.getElementById('notification_toast')),
    elToast: document.getElementById('notification_toast'),
    elIcon: document.getElementById('notification_elIcon'),
    txtMessage: document.getElementById('notification_txtMessage'),
    show: function (message, success) {
        this.txtMessage.innerText = message
        if (success) {
            this.elIcon.classList.replace('fa-times', 'fa-check')
            this.elToast.classList.replace('text-bg-danger', 'text-bg-success')
        } else {
            this.elIcon.classList.replace('fa-check', 'fa-times')
            this.elToast.classList.replace('text-bg-success', 'text-bg-danger')
        }
        this.toast.show()
    }
}

const dishCreation = {
    modal: new bootstrap.Modal(document.getElementById('dishCreation_modal'), {backdrop: 'static', keyboard: false}),
    form: document.getElementById('dishCreation_form'),
    btnCreate: document.getElementById('dishCreation_btnCreate'),
    show: function () {
        this.form.reset()
        this.modal.show()
    },
    confirm: function () {
        if (this.form.reportValidity()) confirmation.show('Confirmar registro', '¿Estás seguro de registrar un platillo con los datos ingresados?', this.form, this.modal)
        else notification.show('Hay campos incompletos o con errores')
    },
}

const dishUpdate = {
    modal: new bootstrap.Modal(document.getElementById('dishUpdate_modal'), {backdrop: 'static', keyboard: false}),
    form: document.getElementById('dishUpdate_form'),
    inpName: document.getElementById('dishUpdate_inpName'),
    inpPrice: document.getElementById('dishUpdate_inpPrice'),
    inpDescription: document.getElementById('dishUpdate_inpDescription'),
    inpCategory: document.getElementById('dishUpdate_inpCategory'),
    inpId: document.getElementById('dishUpdate_inpId'),
    btnUpdate: document.getElementById('dishUpdate_btnUpdate'),
    show: function (id) {
        this.form.reset()
        fetch(context + '/Platillos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `&action=find&id=${id}`
        }).then(res => res.json()).then(({dish}) => {
            this.inpId.value = id
            this.inpName.value = dish.name
            if (dish.description) this.inpDescription.value = dish.description
            this.inpPrice.value = dish.price
            this.inpCategory.value = dish.category.id
            for (ingredient of dish.ingredients) document.getElementById(`dishUpdate_ingredient${ingredient.id}`).checked = true
            this.modal.show()
        }).catch(res => {
            notification.show(res.data.message)
        })

    },
    confirm: function () {
        if (this.form.reportValidity()) confirmation.show('Confirmar actualización', '¿Estás seguro de actualizar la información del platillo?', this.form, this.modal)
        else notification.show('Hay campos incompletos o con errores')
    },
}

const dishDeletion = {
    form: document.getElementById('dishDeletion_form'),
    inpId: document.getElementById('dishDeletion_inpId'),
    show: function (id) {
        this.inpId.value = id
        confirmation.show('Confirmar desactivación', '¿Estás seguro de desactivar este platillo?', this.form)
    },
}