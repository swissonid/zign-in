package github.swissonid.zignin.feature.userregistry.presenation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.swissonid.zignin.feature.userregistry.domain.GetCurrentUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.RegisterNewUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.RemoveCurrentRegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object UserRegistryPresentationModule {

    @Provides
    fun provideGetCurrentUserUseCase(
        userRepository: UserRepository
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(userRepository)
    }

    @Provides
    fun provideRegisterNewUserUseCaseUseCase(
        userRepository: UserRepository
    ): RegisterNewUserUseCase {
        return RegisterNewUserUseCase(userRepository)
    }

    @Provides
    fun provideRemoveCurrentRegisteredUser(
        userRepository: UserRepository
    ): RemoveCurrentRegisteredUser {
        return RemoveCurrentRegisteredUser(userRepository)
    }

    
}