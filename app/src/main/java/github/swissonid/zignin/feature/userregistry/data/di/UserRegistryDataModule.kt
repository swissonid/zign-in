package github.swissonid.zignin.feature.userregistry.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import github.swissonid.zignin.feature.userregistry.data.SharedPreferencesUserRepository
import github.swissonid.zignin.feature.userregistry.domain.UserRepository
import javax.inject.Named

private const val SHARED_PREFERENCES_USER_REGISTRY = "shared_preferences_user_registry"

@Module
@InstallIn(SingletonComponent::class)
object UserRegistryDataModule {

    @Provides
    @Named(SHARED_PREFERENCES_USER_REGISTRY)
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_USER_REGISTRY, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideUserRegistryRepository(
        @Named(SHARED_PREFERENCES_USER_REGISTRY) sharedPreferences: SharedPreferences
    ): UserRepository {
        return SharedPreferencesUserRepository(sharedPreferences)
    }
}