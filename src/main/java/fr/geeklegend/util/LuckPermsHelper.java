package fr.geeklegend.util;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class LuckPermsHelper
{
    public static boolean hasPermission(User user, String permission) {
        ContextManager contextManager = LuckPermsProvider.get().getContextManager();
        ImmutableContextSet contextSet = contextManager.getContext(user).orElseGet(contextManager::getStaticContext);

        CachedPermissionData permissionData = user.getCachedData().getPermissionData(QueryOptions.contextual(contextSet));
        return permissionData.checkPermission(permission).asBoolean();
    }
}
