const confirmation = {
    modalElement: document.getElementById('confirmation_modal'),
    modal: new bootstrap.Modal(document.getElementById('confirmation_modal'), {backdrop: 'static', keyboard: false}),
    txtTitle: document.getElementById('confirmation_txtTitle'),
    txtText: document.getElementById('confirmation_txtText'),
    btnConfirm: document.getElementById('confirmation_btnConfirm'),
    btnCancel: document.getElementById('confirmation_btnCancel'),
    show: function (title, text, form, modal) {
        this.btnConfirm.onclick = () => {
            this.modal.hide()
            form.submit()
            if (modal) modal.show()
        }
        this.btnCancel.onclick = () => {
            this.modal.hide()
            if (modal) modal.show()
        }
        this.txtTitle.innerText = title
        this.txtText.innerText = text
        this.modal.show()
    },
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
        confirmation.show('Confirmar registro', '¿Estás seguro de registrar un platillo con los datos ingresados?', this.form, this.modal)
    },
}
const dishUpdate = {}
const dishDeletion = {
    form: document.getElementById('dishDeletion_form'),
    inpId: document.getElementById('dishDeletion_inpId'),
    show: function (id) {
        this.inpId.value = id
        confirmation.show('Confirmar desactivación', '¿Estás seguro de desactivar este platillo?', this.form)
    },
}