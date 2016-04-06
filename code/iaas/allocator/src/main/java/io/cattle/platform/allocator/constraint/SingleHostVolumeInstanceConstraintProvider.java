package io.cattle.platform.allocator.constraint;

import io.cattle.platform.allocator.dao.AllocatorDao;
import io.cattle.platform.allocator.service.AllocationAttempt;
import io.cattle.platform.allocator.service.AllocationLog;
import io.cattle.platform.core.constants.CommonStatesConstants;
import io.cattle.platform.core.constants.VolumeConstants;
import io.cattle.platform.core.model.Instance;
import io.cattle.platform.core.model.Volume;
import io.cattle.platform.core.util.InstanceHelpers;
import io.cattle.platform.object.ObjectManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class SingleHostVolumeInstanceConstraintProvider implements AllocationConstraintsProvider {

    static final List<Object> IHM_STATES = Arrays.asList(new Object[] { CommonStatesConstants.INACTIVE, CommonStatesConstants.DEACTIVATING,
            CommonStatesConstants.REMOVED, CommonStatesConstants.REMOVING });

    @Inject
    AllocatorDao allocatorDao;

    @Inject
    ObjectManager objectManager;

    @Override
    public void appendConstraints(AllocationAttempt attempt, AllocationLog log, List<Constraint> constraints) {
        Instance instance = attempt.getInstance();
        if (instance == null) {
            return;
        }

        List<Volume> volumes = InstanceHelpers.extractVolumesFromMounts(instance, objectManager);
        for (Volume v : volumes) {
            if (!VolumeConstants.ACCESS_MODE_SINGLE_HOST_RW.equals(v.getAccessMode())) {
                continue;
            }

            if (v.getHostId() != null) {
                boolean hardConstraint = allocatorDao.isVolumeInUseOnHost(v.getId(), v.getHostId());
                constraints.add(new SingleHostVolumeInstanceConstraint(v.getHostId(), v.getId(), hardConstraint));
            }
        }
    }

    @Override
    public boolean isCritical() {
        return true;
    }
}
