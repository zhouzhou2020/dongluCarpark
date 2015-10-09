package com.donglu.carpark.wizard;

import org.eclipse.jface.wizard.Wizard;

import com.dongluhitec.card.common.ui.AbstractWizard;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkCarpark;
import com.dongluhitec.card.domain.db.singlecarpark.SingleCarparkDevice;
import com.dongluhitec.card.domain.util.StrUtil;


public class AddDeviceWizard extends Wizard implements AbstractWizard{
	AddDeviceModel model;
	private AddDeviceBasicPage page;
	
	public AddDeviceWizard(AddDeviceModel model) {
		this.model=model;
		setWindowTitle("添加固定用户");
	}

	@Override
	public void addPages() {
		page = new AddDeviceBasicPage(model);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		String linkAddress = model.getLinkAddress();
		SingleCarparkCarpark carpark = model.getCarpark();
		String identifire = model.getIdentifire();
		String roadType = model.getRoadType();
		
		
		if (StrUtil.isEmpty(model.getAddress())||StrUtil.isEmpty(model.getName())||StrUtil.isEmpty(model.getIp())||
				StrUtil.isEmpty(roadType)||StrUtil.isEmpty(identifire)||StrUtil.isEmpty(carpark)||StrUtil.isEmpty(linkAddress)) {
			page.setErrorMessage("请填写完整信息");
			return false;
		}
		return true;
	}

	public Object getModel() {
		
		return model;
	}

}
