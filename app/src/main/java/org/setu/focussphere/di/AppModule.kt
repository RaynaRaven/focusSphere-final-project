package org.setu.focussphere.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.setu.focussphere.data.database.FocusSphereDatabase
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.data.repository_impl.TaskRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /*
      Defines with functions the dependencies that will be used in the app and
      how they should be created. Also their scope/lifetime
      e.g. @Singleton , @ActivityScoped , @FragmentScoped
    */

    @Provides
    @Singleton
    fun provideFocusSphereDatabase(app: Application) : FocusSphereDatabase {
        return Room.databaseBuilder(
            app,
            FocusSphereDatabase::class.java,
            "focussphere_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: FocusSphereDatabase): TaskRepository {
        return TaskRepositoryImpl(db.dao())
    }

}