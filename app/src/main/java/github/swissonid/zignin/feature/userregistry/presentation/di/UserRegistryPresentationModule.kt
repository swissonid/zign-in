package github.swissonid.zignin.feature.userregistry.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.swissonid.zignin.feature.userregistry.domain.GetCurrentUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.RegisterNewUserUseCase
import github.swissonid.zignin.feature.userregistry.domain.RemoveCurrentRegisteredUserUseCase
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
        removeCurrentUser: RemoveCurrentRegisteredUserUseCase,
        userRepository: UserRepository
    ): RegisterNewUserUseCase {
        return RegisterNewUserUseCase(userRepository, removeCurrentUser)
    }

    @Provides
    fun provideRemoveCurrentRegisteredUser(
        userRepository: UserRepository
    ): RemoveCurrentRegisteredUserUseCase {
        return RemoveCurrentRegisteredUserUseCase(userRepository)
    }


}